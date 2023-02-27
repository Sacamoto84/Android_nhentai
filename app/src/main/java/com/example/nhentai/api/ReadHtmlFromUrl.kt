package com.example.nhentai.api

import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheHTMLRead
import com.example.nhentai.cache.cacheHTMLWrite
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import okhttp3.internal.http2.Http2Reader.Companion.logger
import timber.log.Timber
import java.net.InetSocketAddress
import java.net.Proxy

suspend fun readHtmlFromURL(url : String = "https://nhentai.to/g/403146"): String {
    Timber.i("..readHtmlFromURL")

    val client = HttpClient(Android)//(OkHttp)
    {
        install(Logging) {
            level = LogLevel.ALL
            //logger = Logger.DEFAULT

        //level = LogLevel.HEADERS

        }

        install(HttpTimeout)
        {
            requestTimeoutMillis = 60000
        }


        engine {
            //proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("85.26.146.169", 80)) //85.26.146.169:80
        }



    }

    if (cacheCheck(url))
    {
        //Если в кеше есть страница то ее и читаем
        val html = cacheHTMLRead(url)
        return html
    }
    else
    {
        //В кеше нет данных
        val response: HttpResponse = client.get(url)
        println(response.toString())
        val result = response.bodyAsText()
        //Сохраним в кеш данный html
        cacheHTMLWrite(url, result)
        client.close()
        return result
    }



    //println(response.status)

    //println(response.bodyAsText())
    //client.close()

}