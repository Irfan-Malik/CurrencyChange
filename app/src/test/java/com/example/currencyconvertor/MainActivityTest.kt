package com.example.currencyconvertor

import com.example.currencyconvertor.util.CurrencyTestUtils
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun checkConversionValue() {
        /*
        * if 2 USD value converted to BBD which currency rates is 2 then the result
        * should be 4
        * */
        val asset = CurrencyTestUtils.checkCurrencyConversions(2.0,2.0)
        assert(asset == 4.0)

    }


}