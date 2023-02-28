package com.example.nhentai.cache

import com.example.nhentai.cacheDirTemp
import com.tomclaw.cache.DiskLruCache
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.prepareGet
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File


lateinit var lruCache: DiskLruCache


var lruCacheDownloadSet =
    mutableSetOf<String>() //Список текущих работ по скачиванию, чтобы небыло две работы по скачиванию в кеш


//fun URLtoFilePath(url: String): String {
//    val str = url.substringAfter("://")
//    if (str.last() == '/')
//        str.dropLast(1)
//    val srcFolderPath = contex.getExternalFilesDir("/Cache/$str")
//    return srcFolderPath.toString() + "/${str.substringAfterLast('/')}"
//}


// Версия DiskLruCache
fun URLtoFilePath(url: String): String {
    val file: File? = lruCache.get(url)
    val path = file?.path
    return path ?: "null"
}

//Проверить адресс есть ли в кеше
//Возвращает bool
fun cacheCheck(url: String): Boolean {

    val file: File? = lruCache.get(url)
    var result: Boolean = false

    if (file != null)
        result = true

    Timber.i("..cacheCheck result:$result url:$url")
    return result
}

fun isFileExists(file: File): Boolean {
    return file.exists() && !file.isDirectory
}

////Запись в кеш HTML страницу
//@OptIn(DelicateCoroutinesApi::class)
//suspend fun cacheHTMLWrite(url: String, html: String) {
//    Timber.i("...cacheHTMLWrite $url")
//    GlobalScope.launch(Dispatchers.IO) {
//        try {
//            val file = File.createTempFile("random", ".dat", cacheDirTemp)
//            file.writeText(html)
//            lruCache.put(url, file)
//            Timber.i("HTML сохранен в Кеш $url Файл ${URLtoFilePath(url)}")
//        } catch (e: Exception) {
//            Timber.e(e.message)
//        }
//    }
//}

//Чтение из кеша HTML страницы
suspend fun cacheHTMLRead(url: String): String {
    Timber.i("..cacheHTMLRead")
    val file: File? = lruCache.get(url)
    return file?.readText() ?: ""
}

//Запись в кеш Файл
@OptIn(DelicateCoroutinesApi::class)
fun cacheFileWrite(url: String) {
    Timber.i("..cacheFileWrite $url")

    val request = lruCacheDownloadSet.contains(url)
    if (request == true) {
        //Значит задача есть в списке задачь
        Timber.i("Задача есть в списке на скачивание $url")
        return
    }
    lruCacheDownloadSet.add(url) //Добавили в список задач

    GlobalScope.launch(Dispatchers.IO) {

        val list: MutableList<Byte> = mutableListOf()
        val client = HttpClient(OkHttp)
        {
            install(HttpTimeout)
            {
                requestTimeoutMillis = 26000000
                connectTimeoutMillis = 26000000
                socketTimeoutMillis = 26000000
            }
        }

        try {

            client.prepareGet(url).execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.body()

                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.isEmpty) {
                        val bytes = packet.readBytes()
                        list.addAll(bytes.toList())
                        //Timber.i("Received ${list.size} из ${httpResponse.contentLength()} $url")
                    }

                }
                Timber.i("Файл скачан $url")
                run {
                    val file = File.createTempFile("random", ".dat", cacheDirTemp)
                    file.writeBytes(list.toByteArray())
                    lruCache.put(url, file)
                    Timber.i("Файл сохранен в Кеш $url Файл ${URLtoFilePath(url)} Temp:${file.path}")
                    Timber.i("usedSpace:${lruCache.usedSpace} freeSpace:${lruCache.freeSpace} cacheSize:${lruCache.cacheSize}")
                    lruCacheDownloadSet.remove(url) //Удалили из списка текущий url
                    true
                }

            }

        } catch (e: Exception) {
            Timber.e(e.message)
        }


    }
}
