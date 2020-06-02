package com.dbottillo.replacename

import android.app.Application
import com.dbottillo.replacename.di.DaggerAppComponent
import com.dbottillo.replacename.di.HomeComponent
import com.dbottillo.replacename.di.HomeComponentProvider

class ReplaceNameApp: Application(), HomeComponentProvider{

    private val appComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
    }

    override fun provideHomeComponent(): HomeComponent {
        return appComponent.homeComponent().create()
    }
}