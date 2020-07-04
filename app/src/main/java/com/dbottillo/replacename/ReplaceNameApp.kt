package com.dbottillo.replacename

import android.app.Application
import com.dbottillo.replacename.di.AboutComponent
import com.dbottillo.replacename.di.AboutComponentProvider
import com.dbottillo.replacename.di.DaggerAppComponent
import com.dbottillo.replacename.di.HomeComponent
import com.dbottillo.replacename.di.HomeComponentProvider

class ReplaceNameApp : Application(), HomeComponentProvider, AboutComponentProvider {

    private val appComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
    }

    override fun provideHomeComponent(): HomeComponent {
        return appComponent.homeComponent().create()
    }

    override fun provideAboutComponent(): AboutComponent {
        return appComponent.aboutComponent().create()
    }
}
