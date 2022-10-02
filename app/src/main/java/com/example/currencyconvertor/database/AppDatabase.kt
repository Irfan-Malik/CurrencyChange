package com.example.currencyconvertor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconvertor.database.entities.CurrencyEntity
import com.example.currencyconvertor.database.entities.LatestCurrencies
import com.example.currencyconvertor.remote.model.currency_rates.LatestCurrencyRates

@Database(entities = [CurrencyEntity::class,LatestCurrencies::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}