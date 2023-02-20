package com.example.nhentai

import android.annotation.SuppressLint
import android.content.Context
import com.example.nhentai.model.DynamicNHentai
import javax.inject.Inject
import javax.inject.Singleton


sealed class Screen(val route: String) {
    object Info : Screen("info")
    object Viewer : Screen("viewer")
}

@SuppressLint("StaticFieldLeak")
lateinit var contex: Context

//Структура для текущего выбранной галереи
lateinit var DN: DynamicNHentai


//https://blog.canopas.com/jetpack-compose-with-dagger-hilt-mvvm-and-navcontroller-b6048bb85073
@Singleton
class UserRepository @Inject constructor() {



}