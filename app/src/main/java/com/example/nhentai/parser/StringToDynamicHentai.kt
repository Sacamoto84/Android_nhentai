package com.example.nhentai.parser

import com.example.nhentai.model.DynamicNHentai
import com.example.nhentai.model.NHentaiTag
import com.example.nhentai.model.TagContainer
import com.example.nhentai.model.ThumbContainer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import timber.log.Timber

fun stringToDynamicHentai(html: String): DynamicNHentai {

    println("stringToDynamicHentai")

    val doc: Document = Jsoup.parse(html)
    val bigContainer = doc.getElementById("bigcontainer")

    val coverURL = bigContainer?.getElementById("cover")?.getElementsByTag("a")?.get(0)
        ?.getElementsByTag("img")?.get(0)?.attributes()?.get("src")

    val info = bigContainer?.getElementById("info")
    val h1 = info?.getElementsByTag("h1")?.get(0)?.childNode(0).toString()

    try {
        val h2 = info?.getElementsByTag("h2")?.get(0)?.childNode(0).toString()
    }
    catch (e : Exception)
    {
        Timber.e(e.message)
    }

    val id = info?.getElementsByTag("h3")?.get(0)?.childNode(1).toString().toInt()

    val tags = info?.getElementById("tags")?.getElementsByClass("tag-container field-name")
    val tags1 = info?.getElementById("tags")?.getElementsByClass("tag-container field-name ")

    val tagContainerAll: MutableList<TagContainer> = mutableListOf()

    if (tags1 != null) {

        for (i in tags1) {

            val type = i.childNode(0).toString()
            val size = i.getElementsByTag("a").size

            val t: MutableList<NHentaiTag> = mutableListOf()

            for (ii in 0 until size) {
                val href = i.getElementsByTag("a")[ii]?.attributes()?.get("href")
                val name = i.getElementsByClass("name")[ii].text()
                val count = i.getElementsByClass("count")[ii].text().toInt()

                t.add(NHentaiTag(name, href, count))
            }

            tagContainerAll.add(TagContainer(type, t))

        }
    }

    var pages = 0
    var uploaded = "."

    if (tags != null) {
        for (i in tags) {
            val type = i.childNode(0).toString()
            if (type == "\nPages: ") {
                pages = i.getElementsByClass("name")[0].text().toInt()
            } else {
                uploaded = i.getElementsByTag("time")[0].text()
            }
        }
    }

    val thumbnail =
        doc.getElementById("thumbnail-container")?.getElementsByClass("thumb-container")

    val thumbContainers: MutableList<ThumbContainer> = mutableListOf()//Список иконок

    if (thumbnail != null) {
        for (i in thumbnail) {
            val a = i.childNode(1)
            val href = a.attributes().get("href")
            val src = a.childNode(1).attributes().get("data-src")
            thumbContainers.add(ThumbContainer(href, src))
        }
    }

    val item: DynamicNHentai = DynamicNHentai(
        id = id,
        h1 = h1,
        //h2 = h2,
        urlCover = coverURL,
        tags = tagContainerAll,
        num_pages = pages,
        uploaded = uploaded,
        thumbContainers = thumbContainers,
        selectedPage = 1
    )

    return item

}