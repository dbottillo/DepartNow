package com.dbottillo.replacename.di

import com.dbottillo.replacename.ApiInterface
import com.dbottillo.replacename.AppBuildConfig
import com.dbottillo.replacename.BuildConfig
import com.dbottillo.replacename.DeparturesApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideDeparturesApiService(): DeparturesApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl("https://transportapi.com/v3/uk/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.build())
            .build()
            .create(DeparturesApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideAppBuildConfig(): AppBuildConfig {
        return object : AppBuildConfig {
            override val transportAppKey: String
                get() = BuildConfig.TRANSPORT_APP_KEY

            override val transportAppId: String
                get() = BuildConfig.TRANSPORT_APP_ID
        }
    }
}
