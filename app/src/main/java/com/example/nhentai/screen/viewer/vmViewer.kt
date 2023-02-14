package com.example.nhentai.screen.viewer

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.DN
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.cache.cacheFileCheck
import com.example.nhentai.cache.cacheFileWrite
import com.example.nhentai.model.DynamicNHentai
import com.example.nhentai.parser.stringToUrlOriginal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class vmViewer @Inject constructor(

) : ViewModel() {

    //Нажание на эскиз запросит OriginalUrl и сохранит его в кеш
    fun launchReadOriginalImageFromHref(href : String, index : Int) {
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
            if (OriginalURL?.let { cacheFileCheck(it) } == false)
            {
                cacheFileWrite(OriginalURL)
            }

        }

    }










}