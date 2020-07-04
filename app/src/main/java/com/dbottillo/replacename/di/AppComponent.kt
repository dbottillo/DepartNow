package com.dbottillo.replacename.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HomeModule::class, AppModule::class])
interface AppComponent {
    fun homeComponent(): HomeComponent.Factory

    fun aboutComponent(): AboutComponent.Factory
}
