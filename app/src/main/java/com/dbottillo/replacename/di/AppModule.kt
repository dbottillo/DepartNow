package com.dbottillo.replacename.di

import com.dbottillo.replacename.Navigator
import com.dbottillo.replacename.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }
}
