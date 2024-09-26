package com.dbottillo.departnow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DepartNowApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
