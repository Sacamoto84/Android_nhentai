package com.example.nhentai.api

import com.example.nhentai.cache.URLtoFilePath
import com.example.nhentai.cache.lruCache
import com.example.nhentai.cacheDirTemp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File


object Downloader {

    public val cache = DownloaderClass() //Загрузчик в кеш


}


//Загрузчик
class DownloaderClass {
    init {
        Timber.i("Создание загрузчика")
    }

    val FileChannel = Channel<String>(capacity = UNLIMITED)
    val FileChannelList = Channel<List<String>>(capacity = UNLIMITED)

    val HtmlChannel = Channel<String>(capacity = UNLIMITED)


    val setURLHTML = mutableSetOf<String>() //Список адресов на скачивание
    val setURLFile = mutableSetOf<String>()


    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    fun routine() {

        GlobalScope.launch(Dispatchers.IO) {
            while (!HtmlChannel.isClosedForReceive) {
                val s = HtmlChannel.receive()
                setURLHTML.add(s)
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            while (!FileChannel.isClosedForReceive) {
                val s = FileChannel.receive()
                setURLFile.add(s)
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            while (!FileChannelList.isClosedForReceive) {
                val s = FileChannelList.receive()
                setURLFile.addAll(s)
            }
        }




        //Закачка Html в кеш
//        GlobalScope.launch(Dispatchers.IO) {
//            while (true) {
//                if (setURLHTML.size > 0) {
//                    val url = setURLHTML.first()
//                    setURLHTML.remove(url)
//
//                    GlobalScope.launch(Dispatchers.IO) {
//
//                        val client = HttpClient(CIO)//(OkHttp)
//                        {
//                            install(HttpTimeout)
//                            {
//                                requestTimeoutMillis = Long.MAX_VALUE
//                            }
//                        }
//
//                        try {
//                        Timber.d("url $url")
//                        val response: HttpResponse = client.get(url)
//                        println(response.toString())
//                        val html = response.bodyAsText()
//                        //Сохраним в кеш данный html
//
//                            val file = File.createTempFile("random", ".dat", cacheDirTemp)
//                            file.writeText(html)
//                            lruCache.put(url, file)
//                            Timber.i("HTML сохранен в Кеш $url Файл ${URLtoFilePath(url)}")
//                        } catch (e: Exception) {
//                            Timber.e(e.message)
//                        }
//                        client.close()
//                    }
//
//                }
//                else
//                    delay(10)
//            }
//        }


    }

}

