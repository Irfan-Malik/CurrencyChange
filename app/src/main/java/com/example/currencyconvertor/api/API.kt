package com.example.currencyconvertor.api

import com.example.currencyconvertor.remote.model.Currency
import com.example.currencyconvertor.remote.model.currency_rates.LatestCurrencyRates
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("currencies.json")
    suspend fun onCurrencyList(
        @Query("api_key") apiKey: String,
    ): Currency

    @GET("latest.json")
    suspend fun onCurrencyLatest(
        @Query("app_id") apiKey: String,
        @Query("prettyprint") prettyprint: Boolean = true,
        @Query("show_alternative") show_alternative: Boolean = true,
    ): LatestCurrencyRates
}