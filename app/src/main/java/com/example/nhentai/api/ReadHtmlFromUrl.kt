package com.example.nhentai.api

import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheHTMLRead
import com.example.nhentai.cache.cacheHTMLWrite
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.CacheStorage
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.cache.storage.HttpCacheStorage
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import timber.log.Timber
import java.nio.file.Files
import java.nio.file.Paths

suspend fun readHtmlFromURL(url : String = "https://nhentai.to/g/403146"): String {
    Timber.i("..readHtmlFromURL")

    val client = HttpClient(OkHttp)
    {
        install(Logging) { level = LogLevel.INFO }

        install(HttpTimeout)
        {
            requestTimeoutMillis = 60000
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