package com.example.nhentai

import android.annotation.SuppressLint
import android.content.Context
import com.example.nhentai.model.DynamicNHentai


sealed class Screen(val route: String) {
    object Info : Screen("info")
    object Viewer : Screen("viewer")
}

@SuppressLint("StaticFieldLeak")
lateinit var contex: Context

//Структура для текущего выбранной галереи
lateinit var DN: DynamicNHentai