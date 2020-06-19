package com.dbottillo.replacename.di

import com.dbottillo.replacename.feature.home.HomeActivity
import dagger.Component
import dagger.Subcomponent

@Subcomponent
interface HomeComponent{
    fun inject(activity: HomeActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }
}