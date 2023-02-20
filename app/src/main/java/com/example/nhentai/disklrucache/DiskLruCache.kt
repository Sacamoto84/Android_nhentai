package com.example.nhentai.disklrucache

import com.example.nhentai.disklrucache.journal.JOURNAL_FILE
import com.example.nhentai.disklrucache.journal.JournalException
import com.example.nhentai.disklrucache.journal.JournalWriter
import com.example.nhentai.disklrucache.journal.readJournalIfExists
import com.example.nhentai.disklrucache.journal.writeJournalAtomically
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer

/**
 * A persisted Least Recently Used (LRU) cache. It can be opened/created by [DiskLruCache.open]
 *
 * An LRU cache is a cache that holds strong references to a limited number of values. Each time a value is accessed,
 * it is moved to the head of a queue. When a value is added to a full cache, the value at the end of that queue is
 * evicted and may become eligible for garbage collection.
 */
class DiskLruCache private constructor(
    private val fileSystem: FileSystem,
    private val directory: Path,
    maxSize: Long,
    private val creationScope: CoroutineScope,
    private val keyTransformer: KeyTransformer?,
    initialRedundantJournalEntriesCount: Int,
) {
    private val lruCache = LruCache<String, Path>(
        maxSize = maxSize,
        sizeCalculator = { _, file -> fileSystem.metadata(file).size ?: 0 },
        onEntryRemoved = { _, key, oldValue, _ -> onEntryRemoved(key, oldValue) },
        creationScope = creationScope,
    )

    private val journalMutex = Mutex()
    private val journalFile = directory.resolve(JOURNAL_FILE)
    private var journalWriter =
        JournalWriter(fileSystem.appendingSink(journalFile, mustExist = true).buffer())

    private var redundantJournalEntriesCount = initialRedundantJournalEntriesCount

    /**
     * Returns the file for [key] if it exists in the cache or wait for its creation if it is currently in progress.
     * This returns `null` if a file is not cached and isn't in creation or cannot be created.
     *
     * It may even throw exceptions for unhandled exceptions in the currently in-progress creation block.
     */
    suspend fun get(key: String): String? = lruCache.get(key.transform())?.toString()

    /**
     * Returns the file for [key] if it already exists in the cache or `null` if it doesn't exist or creation is still
     * in progress.
     */
    suspend fun getIfAvailable(key: String): String? =
        lruCache.getIfAvailable(key.transform())?.toString()

    /**
     * Returns the file for [key] if it exists in the cache, its creation is in progress or can be created by
     * [creationFunction]. If a file is returned, it'll be moved to the head of the queue. This returns `null` if a
     * file is not cached and cannot be created. You can imply that the creation has failed by returning `false`.
     * Any unhandled exceptions inside [creationFunction] won't be handled.
     */
    suspend fun getOrPut(key: String, writeFunction: suspend (String) -> Boolean) =
        lruCache.getOrPut(key.transform()) { creationFunction(it, writeFunction) }?.toString()

    /**
     * Creates a new file for [key] using [creationFunction] and returns the new value. Any existing file or
     * in-progress creation of [key] would be replaced by the new function. If a file is created, it'll be moved to the
     * head of the queue. This returns `null` if the file cannot be created. You can imply that the creation has
     * failed by returning `false`. Any unhandled exceptions inside [creationFunction] won't be handled.
     */
    suspend fun put(key: String, writeFunction: suspend (String) -> Boolean) =
        lruCache.put(key.transform()) { creationFunction(it, writeFunction) }?.toString()

    /**
     * Creates a new file for [key] using [creationFunction] and returns a [Deferred]. Any existing file or
     * in-progress creation of [key] would be replaced by the new function. If a file is created, it'll be moved to the
     * head of the queue. You can imply that the creation has failed by returning `null`.
     */
    suspend fun putAsync(key: String, writeFunction: suspend (String) -> Boolean) =
        creationScope.async {
            lruCache.putAsync(key.transform()) { creationFunction(it, writeFunction) }.await()?.toString()
        }

    /**
     * Removes the entry and in-progress creation for [key] if it exists. It returns the previous value for [key].
     */
    suspend fun remove(key: String) {
        // It's fine to consider the file is dirty now. Even if removal failed it's scheduled for
        val transformedKey = key.transform()
        writeDirty(transformedKey)
        lruCache.remove(transformedKey)
    }

    /**
     * Clears the cache, calling [onEntryRemoved] on each removed entry.
     */
    suspend fun clear() {
        close()
        if (fileSystem.metadata(directory).isDirectory) fileSystem.deleteRecursively(directory)
        fileSystem.createDirectories(directory)
    }

    /**
     * Closes the journal file and cancels any in-progress creation.
     */
    suspend fun close() {
        lruCache.removeAllUnderCreation()
        journalMutex.withLock { journalWriter.close() }
    }

    private suspend fun String.transform() =
        keyTransformer?.transform(this) ?: this

    private suspend fun creationFunction(
        key: String,
        writeFunction: suspend (String) -> Boolean,
    ): Path? {
        val tempFile = directory.resolve(key + TEMP_EXT)
        val cleanFile = directory.resolve(key)

        writeDirty(key)
        return if (writeFunction(tempFile.toString()) && fileSystem.exists(tempFile)) {
            fileSystem.atomicMove(tempFile, cleanFile, deleteTarget = true)
            fileSystem.delete(tempFile)
            writeClean(key)
            rebuildJournalIfRequired()
            cleanFile
        } else {
            fileSystem.delete(tempFile)
            writeRemove(key)
            rebuildJournalIfRequired()
            null
        }
    }

    private fun onEntryRemoved(key: String, oldValue: Path) {
        creationScope.launch {
            fileSystem.delete(oldValue)
            fileSystem.delete((oldValue.toString() + TEMP_EXT).toPath())

            writeRemove(key)
            rebuildJournalIfRequired()
        }
    }

    private suspend fun writeClean(key: String) = journalMutex.withLock {
        journalWriter.writeClean(key)
        redundantJournalEntriesCount++
    }

    private suspend fun writeDirty(key: String) = journalMutex.withLock {
        journalWriter.writeDirty(key)
    }

    private suspend fun writeRemove(key: String) = journalMutex.withLock {
        journalWriter.writeRemove(key)
        redundantJournalEntriesCount += 3
    }

    private suspend fun rebuildJournalIfRequired() {
        if (redundantJournalEntriesCount < REDUNDANT_ENTRIES_THRESHOLD) return

        journalMutex.withLock {
            // Check again to make sure that there was not an ongoing rebuild request
            if (redundantJournalEntriesCount < REDUNDANT_ENTRIES_THRESHOLD) return

            journalWriter.close()

            val (cleanKeys, dirtyKeys) = lruCache.getAllKeys()
            fileSystem.writeJournalAtomically(directory, cleanKeys, dirtyKeys)

            journalWriter =
                JournalWriter(fileSystem.appendingSink(journalFile, mustExist = true).buffer())
            redundantJournalEntriesCount = 0
        }
    }

    companion object {
        /**
         * Opens the persisted Least Recently Used (LRU) cache in the provided directory or creates a new one if it
         * doesn't already exist.
         *
         * @param directoryPath The directory for storing the journal and cached files. To prevent data loss, it shouldn't be used
         * for storing files not managed by this class.
         * @param maxSize The max size of this cache in bytes, excluding the size of the journal file.
         * @param creationDispatcher The coroutine dispatcher used for executing `creationFunction` of put requests.
         * @param keyTransformer function used for transforming keys to be safe for filenames. See [KeyTransformer]
         */
        suspend fun open(
            directoryPath: String,
            maxSize: Long,
            creationDispatcher: CoroutineDispatcher,
            keyTransformer: KeyTransformer? = SHA256KeyHasher,
        ) = open(
            fileSystem = getSystemFileSystem(),
            directory = directoryPath.toPath(),
            maxSize = maxSize,
            creationDispatcher = creationDispatcher,
            keyTransformer = keyTransformer,
        )

        internal suspend fun open(
            fileSystem: FileSystem,
            directory: Path,
            maxSize: Long,
            creationDispatcher: CoroutineDispatcher,
            keyTransformer: KeyTransformer? = SHA256KeyHasher,
        ): DiskLruCache {
            require(maxSize > 0) { "maxSize must be positive value" }

            // Make sure that journal directory exists
            fileSystem.createDirectories(directory)

            val journalData = try {
                fileSystem.readJournalIfExists(directory)
            } catch (ex: JournalException) {
                // Journal is corrupted - Clear cache
                fileSystem.deleteContents(directory)
                null
            }

            // Delete dirty entries
            if (journalData != null) {
                for (key in journalData.dirtyEntriesKeys) {
                    fileSystem.delete(directory.resolve(key + TEMP_EXT))
                }
            }

            // Rebuild journal if required
            var redundantJournalEntriesCount = journalData?.redundantEntriesCount ?: 0

            if (journalData == null) {
                fileSystem.writeJournalAtomically(directory, emptyList(), emptyList())
            } else if (
                journalData.redundantEntriesCount >= REDUNDANT_ENTRIES_THRESHOLD &&
                journalData.redundantEntriesCount >= journalData.cleanEntriesKeys.size
            ) {
                fileSystem
                    .writeJournalAtomically(directory, journalData.cleanEntriesKeys, emptyList())
                redundantJournalEntriesCount = 0
            }

            val cache = DiskLruCache(
                fileSystem,
                directory,
                maxSize,
                CoroutineScope(creationDispatcher),
                keyTransformer,
                redundantJournalEntriesCount,
            )

            if (journalData != null) {
                for (key in journalData.cleanEntriesKeys) {
                    //cache.lruCache.put(key, directory.resolve(key))
                }
            }

            return cache
        }

        private const val TEMP_EXT = ".tmp"
        private const val REDUNDANT_ENTRIES_THRESHOLD = 2000
    }
}