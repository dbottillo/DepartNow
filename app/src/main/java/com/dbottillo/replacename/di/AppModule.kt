package com.dbottillo.replacename.di

import com.dbottillo.replacename.Navigator
import com.dbottillo.replacename.NavigatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }
}