package com.example.currencyconvertor.util

object CurrencyTestUtils {

    fun checkCurrencyConversions(givenCurrency : Double, conversionRate: Double):Double{
        return givenCurrency * conversionRate
    }
}