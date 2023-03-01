package com.example.nhentai

import android.app.Application
import com.bugsnag.android.Bugsnag
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Bugsnag.start(this)
    }

}

