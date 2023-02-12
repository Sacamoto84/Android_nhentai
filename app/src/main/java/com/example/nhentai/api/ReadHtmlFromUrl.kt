package com.example.nhentai.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

suspend fun readHtmlFromURL(url : String = "https://nhentai.to/g/403147"): String {
    println("readHtmlFromURL")
    val client = HttpClient(CIO)
    {
        install(HttpCache){
            //val cacheFile = Files.createDirectories(Paths.get("build/cache")).toFile()
            //publicStorage(FileStorage(cacheFile))
        }

        install(Logging) { level = LogLevel.INFO }
    }

    val response: HttpResponse = client.get(url)
    //println(response.status)
    println(response.toString())
    //println(response.bodyAsText())
    client.close()
    return response.bodyAsText()
}