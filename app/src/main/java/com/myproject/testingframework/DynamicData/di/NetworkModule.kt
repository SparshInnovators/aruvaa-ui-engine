package com.myproject.testingframework.DynamicData.di

import com.myproject.testingframework.DynamicData.api.quotesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val baseUrl = "https://api.api-ninjas.com"

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun createApi(retrofit: Retrofit): quotesApi {
        return retrofit.create(quotesApi::class.java)
    }
}