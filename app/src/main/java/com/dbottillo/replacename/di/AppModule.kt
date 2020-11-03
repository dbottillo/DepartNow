package com.dbottillo.replacename.di

import com.dbottillo.replacename.ApiInterface
import com.dbottillo.replacename.Navigator
import com.dbottillo.replacename.NavigatorImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ApiInterface::class.java)
    }
}
