package com.dbottillo.replacename

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReplaceNameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
