package com.example.nhentai.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import timber.log.Timber

suspend fun readHtmlFromURL(url : String = "https://nhentai.to/g/403146"): String {
    Timber.i("..readHtmlFromURL")

    val client = HttpClient(OkHttp)
    {
        //install(HttpCache){
            //val cacheFile = Files.createDirectories(Paths.get("build/cache")).toFile()
            //publicStorage(FileStorage(cacheFile))
        //}

        install(Logging) { level = LogLevel.INFO }
    }

    val response: HttpResponse = client.get(url)
    //println(response.status)
    println(response.toString())
    //println(response.bodyAsText())
    client.close()
    return response.bodyAsText()
}