package com.example.currencyconvertor.di

import com.example.currencyconvertor.App
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationInstance(): App {
        return App()
    }

    @Provides
    @Singleton
    fun provideGson():Gson{
        return Gson()
    }
}