package com.example.nhentai.api

import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheHTMLRead
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import timber.log.Timber

private val client = HttpClient(CIO)//(OkHttp)
{
    install(Logging) {
        level = LogLevel.INFO
    }

    install(HttpTimeout)
    {
        requestTimeoutMillis = Long.MAX_VALUE
    }
}

suspend fun readHtmlFromURL(url : String = "https://nhentai.to/g/403146"): String {
    Timber.i("..readHtmlFromURL $url ")

    if (cacheCheck(url))
    {
        //Если в кеше есть страница то ее и читаем
        val html = cacheHTMLRead(url)
        return html
    }
    else
    {
        Timber.i("В кеше нет данных")
        lateinit var response: HttpResponse
        try {
            response = client.get(url)
            println(response.toString())
            return response.bodyAsText()
        } catch (e: Exception) {
            Timber.e("Ошибка " + e.message)
        }

        //Сохраним в кеш данный html

        //Downloader.cache.HtmlChannel.send(url)

        //client.close()
        return ""
    }



    //println(response.status)

    //println(response.bodyAsText())
    //client.close()

}