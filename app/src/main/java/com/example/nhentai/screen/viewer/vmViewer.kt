package com.example.nhentai.screen.viewer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.DN
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.cache.URLtoFilePath
import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheFileWrite
import com.example.nhentai.parser.stringToUrlOriginal
import com.jakewharton.picnic.table
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class vmViewer @Inject constructor(

) : ViewModel() {


    //
    private val _selectPageState = MutableStateFlow<Int>(DN.selectedPage)
    val selectPageState: StateFlow<Int>
        // = _selectPageState
        get() = _selectPageState
    //


    init {
        Timber.i(
            table {
                cellStyle {
                    border = true
                }
                row("Создание вьюмодели vmViewer")
            }.toString()
        )


        viewModelScope.launch(Dispatchers.IO) {
            _selectPageState.collect {
                Timber.i("collect = $it")




            }
        }


    }

    var address = mutableStateOf("") //Адресс показываемой картинки
    var selectedPage by mutableStateOf("") //Номер выбранной страницы


    override fun onCleared() {
        super.onCleared()
        Timber.i(
            table {
                cellStyle {
                    border = true
                }
                row("vmViewer...onCleared")
            }.toString()
        )
    }

    //////////////////////////////////////////////////////
    //Навигация

    //Создать адресс следующей картинки
    fun next() {
        Timber.i("next()")

        if (DN.selectedPage < DN.num_pages)
            DN.selectedPage++

        _selectPageState.update {
            DN.selectedPage
        }

        calculateAddress()
    }

    //Создать адресс прошлой картинки
    fun previous() {
        Timber.i("previous()")

        if (DN.selectedPage > 1)
            DN.selectedPage--

        _selectPageState.update {
            DN.selectedPage
        }

        calculateAddress()
    }

    //Создать адресс первой картинки
    fun first() {
        Timber.i("first()")
        DN.selectedPage = 1

        _selectPageState.update {
            DN.selectedPage
        }

        calculateAddress()
    }


    fun last() {
        Timber.i("last()")
        DN.selectedPage = DN.num_pages.toInt()

        _selectPageState.update {
            DN.selectedPage
        }

        calculateAddress()
    }


    fun calculateAddress() {
        Timber.i("......calculateAddress()")

        var url = DN.thumbContainers[DN.selectedPage - 1].urlOriginal.toString()

        Timber.i("1 $url")

        var originalURL: String

        if (url == "null") {
            Timber.i("Нет urlOriginal")



            runBlocking {
                var s =
                    "https://nhentai.to${DN.thumbContainers[DN.selectedPage - 1].href.toString()}"

                if (s.last() == '/')
                    s = s.dropLast(1)

                val html = readHtmlFromURL(s)
                originalURL = stringToUrlOriginal(html)!!

                url = originalURL

                DN.thumbContainers[DN.selectedPage - 1].urlOriginal = originalURL
                Timber.i("OriginalURL = $originalURL")

            }

            if (DN.thumbContainers[DN.selectedPage - 1].urlOriginal.toString() == "null")
                return
        }

        Timber.i("2")


        GlobalScope.launch(Dispatchers.Main) {


            address.value = if (!cacheCheck(url)) {
                cacheFileWrite(url)
                url
            } else {
                val s = URLtoFilePath(url)
                Timber.i("calculateAddress $s")
                s
            }
            Timber.i("address ${address.value}")
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

//            //Если нет файла то создадим для него кеш
//            if (OriginalURL?.let { cacheCheck(it) } == false) {
//                cacheFileWrite(OriginalURL)
//            }

        }

    }


}