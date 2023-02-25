package com.example.nhentai.screen.viewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.DNselectedPage
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.cache.URLtoFilePath
import com.example.nhentai.cache.cacheCheck
import com.example.nhentai.cache.cacheFileWrite
import com.example.nhentai.parser.stringToUrlOriginal
import com.jakewharton.picnic.table
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class vmViewer @Inject constructor(

) : ViewModel() {

    //
    private val _selectPageState = MutableStateFlow<Int>(DNselectedPage)
    val selectPageState = _selectPageState.asStateFlow()
    //
    private val _selectAddress = MutableStateFlow<String>("")
    val selectAddress = _selectAddress.asStateFlow()
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
                calculateAddressCoroutine()
            }

        }

    }

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

//        if (DNselectedPage < DNGallery.value?.num_pages!!)
//            DNselectedPage++

        _selectPageState.update {
            DNselectedPage
        }

        //calculateAddress()
    }

    //Создать адресс прошлой картинки
    fun previous() {
        Timber.i("previous()")

        if (DNselectedPage > 1)
            DNselectedPage--

        _selectPageState.update {
            DNselectedPage
        }

        //calculateAddress()
    }

    //Создать адресс первой картинки
    fun first() {
        Timber.i("first()")
        DNselectedPage = 1

        _selectPageState.update {
            DNselectedPage
        }

        //calculateAddress()
    }

    fun last() {
        Timber.i("last()")

//        DNselectedPage = DNGallery.value?.num_pages!!

        _selectPageState.update {
            DNselectedPage
        }

        //calculateAddress()
    }

    fun calculateAddressCoroutine() {




//        viewModelScope.launch(Dispatchers.IO) {
//
//            var url = DNthumbContainer.value?.get(DNselectedPage - 1)?.urloriginal.toString()
//
//            Timber.i("......calculateAddressCoroutine() urlOriginal $url")
//
//            if (url == "null") {
//                Timber.i("Нет urlOriginal")
//                var s =
//                    "https://nhentai.to${DNthumbContainer.value?.get(DNselectedPage - 1)?.href.toString()}"
//                if (s.last() == '/') s = s.dropLast(1)
//                val html = readHtmlFromURL(s)
//                val originalURL = stringToUrlOriginal(html)!!
//                url = originalURL
//                DNthumbContainer.value?.get(DNselectedPage - 1)?.urloriginal = originalURL
//                Timber.i("OriginalURL = $originalURL")
//                calculateAddressCoroutine()
//            } else {
//
//                _selectAddress.value = if (!cacheCheck(url)) {
//                    cacheFileWrite(url)
//                    url
//                } else {
//                    val s = URLtoFilePath(url)
//                    Timber.i("calculateAddress $s")
//                    s
//                }
//                Timber.i("Image address ${_selectAddress.value}")
//            }
//
//        }





    }


}