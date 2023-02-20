package com.example.nhentai.cache

import com.example.nhentai.contex
import com.example.nhentai.disklrucache.DiskLruCache
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.prepareGet
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.File

lateinit var HTTPCacheFolderPath: String //Корневая папка где лежит кеш

lateinit var lruCache: DiskLruCache

//fun URLtoFilePath(url: String): String {
//    val str = url.substringAfter("://")
//    if (str.last() == '/')
//        str.dropLast(1)
//    val srcFolderPath = contex.getExternalFilesDir("/Cache/$str")
//    return srcFolderPath.toString() + "/${str.substringAfterLast('/')}"
//}


// Версия DiskLruCache
suspend fun URLtoFilePath(url: String): String {
    return lruCache.getIfAvailable(url).toString()
}


fun filePath(str: String): String {
    val srcFolderPath = contex.getExternalFilesDir("/Cache/$str")
    return srcFolderPath.toString() + "/${str.substringAfterLast('/')}"
}

//Проверить адресс есть ли в кеше
//Возвращает bool
suspend fun cacheCheck(url: String): Boolean {
    var result: Boolean = false
    var result2: Boolean = false

    try {
        result = lruCache.getIfAvailable(url) != null
        result2 = if (result) {
            isFileExists(File(URLtoFilePath(url)))
        } else false
    } catch (e: Exception) {
        Timber.i(e.message)
    }

    Timber.i("..cacheCheck result:$result result2:$result2 url:$url")
    return result && result2
}

fun isFileExists(file: File): Boolean {
    return file.exists() && !file.isDirectory
}

//Запись в кеш HTML страницу
suspend fun cacheHTMLWrite(url: String, html: String) {
    Timber.i("..cacheHTMLWrite $url")
    try {
        lruCache.put(url) {
            //Timber.i("cacheHTMLWrite 11111111 it $it")
            File(it).writeText(html)
            true
        }
    } catch (e: Exception) {
        Timber.e(e.message)
    }
}

//Чтение из кеша HTML страницы
suspend fun cacheHTMLRead(url: String): String {
    Timber.i("..cacheHTMLRead")
    val file = lruCache.getIfAvailable(url)?.let { File(it) }
    return file?.readText() ?: ""
}


////////////////////////////////////////////////////
// Файлы не Html
////////////////////////////////////////////////////

//fun URLtoFilePathFile(url: String): String {
//    val str = url.substringAfter("://")
//    if (str.last() == '/')
//        str.dropLast(1)
//
//    return contex.getExternalFilesDir("/Cache/${str.substringBeforeLast('/')}")
//        .toString() + "/" + str.substringAfterLast('/')
//}

////Проверить адресс есть ли в кеше
////Возвращает bool
//fun cacheFileCheck(url: String): Boolean {
//    Timber.i("..cacheCheck $url")
//    val result = isFileExists(File(URLtoFilePathFile(url)))
//    Timber.i("..cacheCheck result: $result")
//    return result
//}

//Запись в кеш Файл
@OptIn(DelicateCoroutinesApi::class)
fun cacheFileWrite(url: String) {
    Timber.i("..cacheFileWrite $url")

    // Создаем временный файл
    //val tempFile = File.createTempFile("TempFile", ".tmp")


    val list: MutableList<Byte> = mutableListOf()

    GlobalScope.launch(Dispatchers.IO) {
        val client = HttpClient(CIO)
        {
            install(HttpTimeout)
            {
                requestTimeoutMillis = 60000
            }
        }
        client.prepareGet(url).execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()

            try {

                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())

                    while (!packet.isEmpty) {
                        val bytes = packet.readBytes()

                        list.addAll(bytes.toList())

                        //tempFile.appendBytes(bytes)
                        Timber.i("Received ${list.size} bytes from ${httpResponse.contentLength()} $url")
                    }

                }

                //Timber.i("Файл скачан $url")


                runBlocking {

                    val r = lruCache.putAsync(url) {
                        File(it).writeBytes(list.toByteArray())
                        Timber.i("Файл сохранен в Кеш $url Файл ${URLtoFilePath(url)}")
                        true
                    }
                    r.await()
                    Timber.i("Файл ${URLtoFilePath(url)}")

                }


            } catch (e: Exception) {
                // Обработка исключения
                Timber.e(e.message)
            } finally {
                // Удаляем временный файл после того, как вы закончите с ним работать
                //tempFile.delete()
            }


        }
    }
}

//val source = File("source.txt")
//val destination = File("destination.txt")
//source.copyTo(destination)

