package com.example.nhentai.disklrucache

import okio.Closeable
import okio.FileSystem
import okio.Path
import okio.use

//internal expect fun getSystemFileSystem(): FileSystem

//fileSystem: FileSystem = FileSystem.SYSTEM
fun getSystemFileSystem(): FileSystem = FileSystem.SYSTEM

fun FileSystem.deleteContents(dir: Path) {
    list(dir).forEach { deleteRecursively(it) }
}

fun FileSystem.atomicMove(source: Path, target: Path, deleteTarget: Boolean) {
    if (deleteTarget) delete(target)
    atomicMove(source, target)
}

inline fun <T : Closeable?, R> T.nullableUse(block: (T) -> R?): R? = try {
    use(block)
} catch (ex: NullPointerException) {
    null
}