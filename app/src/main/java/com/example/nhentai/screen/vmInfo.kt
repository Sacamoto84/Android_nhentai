package com.example.nhentai.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhentai.api.readHtmlFromURL
import com.example.nhentai.model.DynamicNHentai
import com.example.nhentai.parser.stringToDynamicHentai
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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




    ////////////////////////////////////////////////////////
    fun launchReadFromId(id : Int = 403147) {
        Timber.i("...launchReadFromId()")
        viewModelScope.launch(Dispatchers.IO) {
            Timber.i("Ok1")
            val html = readHtmlFromURL("https://nhentai.to/g/$id")
            Timber.i("Ok2")
            DN = stringToDynamicHentai(html)
            Timber.i("Ok3")
        }
    }

}