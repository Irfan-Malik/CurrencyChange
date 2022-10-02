package com.example.currencyconvertor.repository

import android.content.Context
import android.util.Log
import com.example.currencyconvertor.api.API
import com.example.currencyconvertor.database.AppDao
import com.example.currencyconvertor.database.entities.CurrencyEntity
import com.example.currencyconvertor.database.entities.LatestCurrencies
import com.example.currencyconvertor.util.Resource
import com.example.currencyconvertor.util.Utility
import com.example.currencyconvertor.util.Utility.networkBoundResource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

class CurrencyRepository(
    private val api: API,
    private val appDao: AppDao,
    private val gson: Gson,
    private val context: Context
) {

    fun onCurrencyList(
        apiKey: String,
    ): Flow<Resource<CurrencyEntity>> {
        Log.e("checkConnection", "checkConnection = " + (Utility.checkConnection(context)))
        return networkBoundResource(
            query = { appDao.queryCurrenciesResponse() },
            fetch = { api.onCurrencyList(apiKey) },
            saveFetchResult = { items ->
                appDao.insertCurrenciesResponse(
                    CurrencyEntity(
                        0,
                        gson.toJson(items)
                    )
                )
            },
            onFetchFailed = { throwable -> Log.e("ERROR", throwable.message.toString()) },
            shouldFetch = { (Utility.checkConnection(context)) }
        )
    }


    suspend fun onCurrencyLatestRate(
        apiKey: String,
    ): Flow<Resource<LatestCurrencies>> {
        return networkBoundResource(
            query = { appDao.queryCurrenciesRatesResponse() },
            fetch = { api.onCurrencyLatest(apiKey) },
            saveFetchResult = { items ->
                appDao.insertCurrenciesRatesResponse(
                    LatestCurrencies(
                        0,
                        gson.toJson(items)
                    )
                )
            },
            onFetchFailed = { throwable -> Log.e("ERROR", throwable.message.toString()) },
            shouldFetch = { (Utility.checkConnection(context)) }
        )
    }
}