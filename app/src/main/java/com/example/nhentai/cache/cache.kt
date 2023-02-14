package com.example.nhentai.cache

import com.example.nhentai.contex
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.prepareGet
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File

lateinit var HTTPCacheFolderPath: String //Корневая папка где лежит кеш

fun URLtoFilePath(url: String): String {
    val str = url.substringAfter("://")
    if (str.last() == '/')
        str.dropLast(1)
    val srcFolderPath = contex.getExternalFilesDir("/Cache/$str")
    return srcFolderPath.toString() + "/${str.substringAfterLast('/')}"
}

fun filePath(str: String): String {
    val srcFolderPath = contex.getExternalFilesDir("/Cache/$str")
    return srcFolderPath.toString() + "/${str.substringAfterLast('/')}"
}

//Проверить адресс есть ли в кеше
//Возвращает bool
fun cacheCheck(url: String): Boolean {
    Timber.i("..cacheCheck $url")
    val result = isFileExists(File(URLtoFilePath(url)))
    Timber.i("..cacheCheck result: $result")
    return result
}

fun isFileExists(file: File): Boolean {
     return file.exists() && !file.isDirectory
}

//Запись в кеш HTML страницу
fun cacheHTMLWrite(url: String, html: String) {
    Timber.i("..cacheHTMLWrite $url")
    val file = File(URLtoFilePath(url))
    try {
        file.writeText(html)
    } catch (e: Exception) {
        Timber.e(e.message)
    }
}

//Чтение из кеша HTML страницы
fun cacheHTMLRead(url: String): String {
    Timber.i("..cacheHTMLRead")
    val file = File(URLtoFilePath(url))
    return file.readText()
}











////////////////////////////////////////////////////
// Файлы не Html
////////////////////////////////////////////////////

fun URLtoFilePathFile(url: String): String {
    val str = url.substringAfter("://")
    if (str.last() == '/')
        str.dropLast(1)

    return contex.getExternalFilesDir("/Cache/${str.substringBeforeLast('/')}")
        .toString() + "/" + str.substringAfterLast('/')
}

//Проверить адресс есть ли в кеше
//Возвращает bool
fun cacheFileCheck(url: String): Boolean {
    Timber.i("..cacheCheck $url")
    val result = isFileExists(File(URLtoFilePathFile(url)))
    Timber.i("..cacheCheck result: $result")
    return result
}

//Запись в кеш Файл
fun cacheFileWrite(url: String) {
    Timber.i("..cacheFileWrite $url")
    val file = File(URLtoFilePathFile(url))
    GlobalScope.launch {
        val client = HttpClient(CIO)
        client.prepareGet(url).execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()
            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                while (!packet.isEmpty) {
                    val bytes = packet.readBytes()

                    file.appendBytes(bytes)

                    Timber.i("Received ${file.length()} bytes from ${httpResponse.contentLength()}")
                }
            }
            Timber.i("A file saved to ${file.path}")
        }
    }
}



