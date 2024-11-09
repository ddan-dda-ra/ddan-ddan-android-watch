package com.ddanddan.watch.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WatchApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}