package com.dbottillo.replacename.di

import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [HomeModule::class, AppModule::class])
interface AppComponent{
    //fun navigator(): AppNavigator

    fun homeComponent(): HomeComponent.Factory
}

interface AppNavigator