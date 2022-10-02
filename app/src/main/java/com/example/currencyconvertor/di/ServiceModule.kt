package com.example.currencyconvertor.di

import com.example.currencyconvertor.api.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit) : API = retrofit.create(API::class.java)
}