package com.example.nhentai.screen.viewer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.DN
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.cache.URLtoFilePathFile
import com.example.nhentai.cache.cacheFileCheck
import com.example.nhentai.cache.cacheFileWrite
import com.example.nhentai.parser.stringToUrlOriginal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class vmViewer @Inject constructor(

) : ViewModel() {

    init {
        Timber.i("Создание вьюмодели vmViewer")
    }


    var address by mutableStateOf("") //Адресс показываемой картинки

    var selectedPage by mutableStateOf("") //Номер выбранной страницы



    //////////////////////////////////////////////////////
    //Навигация

    //Создать адресс следующей картинки
    fun next() {
        Timber.i("next()")

        if (DN.selectedPage < DN.num_pages)
            DN.selectedPage++

        calculateAddress()
    }

    //Создать адресс прошлой картинки
    fun previous() {
        Timber.i("previous()")

        if (DN.selectedPage > 1)
            DN.selectedPage--

        calculateAddress()
    }

    //Создать адресс первой картинки
    fun first() {
        Timber.i("first()")
        DN.selectedPage = 1
        calculateAddress()
    }


    fun last() {
        Timber.i("last()")
        DN.selectedPage = DN.num_pages.toInt()
        calculateAddress()
    }


    fun calculateAddress() {
        Timber.i("calculateAddress()")
        val url = DN.thumbContainers[DN.selectedPage - 1].urlOriginal.toString()
        address = if (!cacheFileCheck(url)) {
            url
        } else {
            URLtoFilePathFile(url)
        }
        selectedPage = DN.selectedPage.toString()
    }
//////////////////////////////////////////////////////

    //Нажание на эскиз запросит OriginalUrl и сохранит его в кеш
    fun launchReadOriginalImageFromHref(href: String, index: Int) {
        Timber.i("...launchReadOriginalImageFromHref()")

        viewModelScope.launch(Dispatchers.IO) {
            Timber.i("Ok1")
            var s = "https://nhentai.to${href}"

            if (s.last() == '/')
                s = s.dropLast(1)

            val html = readHtmlFromURL(s)

            Timber.i("Ok2")
            val OriginalURL = stringToUrlOriginal(html)

            DN.thumbContainers[index].urlOriginal = OriginalURL

            Timber.i("OriginalURL = $OriginalURL")

            //Если нет файла то создадим для него кеш
            if (OriginalURL?.let { cacheFileCheck(it) } == false) {
                cacheFileWrite(OriginalURL)
            }

        }

    }


}