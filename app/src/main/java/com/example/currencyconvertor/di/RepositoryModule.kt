package com.example.currencyconvertor.di

import android.content.Context
import com.example.currencyconvertor.api.API
import com.example.currencyconvertor.database.AppDao
import com.example.currencyconvertor.repository.CurrencyRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCurrencyRepository(
        api: API,
        appDao: AppDao,
        gson: Gson,
        @ApplicationContext context: Context
    ):CurrencyRepository{
        return CurrencyRepository(api,appDao,gson,context)
    }
}