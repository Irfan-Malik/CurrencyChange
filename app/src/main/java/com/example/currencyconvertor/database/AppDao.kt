package com.example.currencyconvertor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconvertor.database.entities.CurrencyEntity
import com.example.currencyconvertor.database.entities.LatestCurrencies
import com.example.currencyconvertor.remote.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrenciesResponse(currency: CurrencyEntity) : Long

    @Query("SELECT * FROM currency")
    fun queryCurrenciesResponse(): Flow<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrenciesRatesResponse(latestCurrencyRates: LatestCurrencies) : Long

    @Query("SELECT * FROM latestCurrency")
    fun queryCurrenciesRatesResponse(): Flow<LatestCurrencies>
}