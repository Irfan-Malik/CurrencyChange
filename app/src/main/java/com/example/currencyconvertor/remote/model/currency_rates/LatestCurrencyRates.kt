package com.example.currencyconvertor.remote.model.currency_rates

data class LatestCurrencyRates(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: Rates,
    val timestamp: Int
)