package com.example.nhentai.screen.info

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.model.DynamicNHentai
import com.example.nhentai.parser.stringToDynamicHentai
import com.example.nhentai.parser.stringToUrlOriginal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class vmInfo @Inject constructor(
    //@ApplicationContext
    //private val context: Context,
) : ViewModel() {

    lateinit var DN: DynamicNHentai

    var ReedDataComplete  = mutableStateOf(false) //Признак того что данные прочитаны полностью

    ////////////////////////////////////////////////////////
    fun launchReadFromId(id : Int = 403147) {
        Timber.i("...launchReadFromId()")
        ReedDataComplete.value = false
        viewModelScope.launch(Dispatchers.IO) {
            Timber.i("Ok1")
            val html = readHtmlFromURL("https://nhentai.to/g/$id")
            Timber.i("Ok2")
            DN = stringToDynamicHentai(html)
            Timber.i("Ok3")
            ReedDataComplete.value = true
        }
    }



    fun launchReadOriginalImageFromHref(href : String) {
        Timber.i("...launchReadOriginalImageFromHref()")

        viewModelScope.launch(Dispatchers.IO) {
            Timber.i("Ok1")
            val html = readHtmlFromURL("https://nhentai.to${href}".dropLast(1))
            Timber.i("Ok2")
            val OriginalURL = stringToUrlOriginal(html)
            Timber.i("OriginalURL = $OriginalURL")
            ReedDataComplete.value = true
        }

    }



}