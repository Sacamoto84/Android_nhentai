package com.example.nhentai

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nhentai.model.DynamicNHentai
import com.example.nhentai.room.EntityThumbContainer
import com.example.nhentai.room.Gallery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed class Screen(val route: String) {
    object Info : Screen("info")
    object Viewer : Screen("viewer")
}


var DNselectedPage = 1

var GlobalId by mutableStateOf(400000)

var x = MutableLiveData<Int>()


@SuppressLint("StaticFieldLeak")
lateinit var contex: Context

//https://blog.canopas.com/jetpack-compose-with-dagger-hilt-mvvm-and-navcontroller-b6048bb85073
@Singleton
class UserRepository @Inject constructor() {

}