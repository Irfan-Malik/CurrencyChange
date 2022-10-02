package com.example.currencyconvertor.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertor.database.entities.CurrencyEntity
import com.example.currencyconvertor.database.entities.LatestCurrencies
import com.example.currencyconvertor.remote.model.Currency
import com.example.currencyconvertor.remote.model.currency_rates.CurrencyItem
import com.example.currencyconvertor.remote.model.currency_rates.LatestCurrencyRates
import com.example.currencyconvertor.repository.CurrencyRepository
import com.example.currencyconvertor.util.Constants
import com.example.currencyconvertor.util.Resource
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private var currenciesLiveData: MutableLiveData<Resource<CurrencyEntity>> = MutableLiveData()
    var currenciesLatestRatesLiveData: MutableLiveData<Resource<LatestCurrencies>> =
        MutableLiveData()
    lateinit var latestCurrenciesRates: LatestCurrencyRates

    private var selected_Country_Currency = MutableLiveData<Double>()
    val selectedCountryCurrency: LiveData<Double>
        get() = selected_Country_Currency

    private var list = MutableLiveData<List<String>>()
    val currenciesList: LiveData<List<String>>
        get() = list


    fun getCurrencies() {
        viewModelScope.launch {
            repository.onCurrencyList(
                Constants.API_KEY
            ).onStart {
                withContext(Dispatchers.Main) {
                    currenciesLiveData.value = Resource(
                        Resource.Status.LOADING,
                        null,
                        ""
                    )
                }
            }.catch { error ->
                withContext(Dispatchers.Main) {
                    currenciesLiveData.value = Resource(
                        Resource.Status.ERROR,
                        null,
                        error.message.toString()
                    )
                }

            }.collect { result ->
                withContext(Dispatchers.Main) {
                    result.data?.let { getCountriesList(it) }
                    currenciesLiveData.value = Resource(
                        Resource.Status.SUCCESS,
                        result.data,
                        "successfully"
                    )
                }

            }
        }
    }

    fun getCurrenciesRates() {
        viewModelScope.launch {
            repository.onCurrencyLatestRate(
                Constants.API_KEY
            ).onStart {
                withContext(Dispatchers.Main) {
                    currenciesLatestRatesLiveData.value = Resource(
                        Resource.Status.LOADING,
                        null,
                        ""
                    )
                }
            }.catch { error ->
                withContext(Dispatchers.Main) {
                    Resource(
                        Resource.Status.ERROR,
                        null,
                        error.message.toString()
                    ).also { currenciesLatestRatesLiveData.value = it }
                }

            }.collect { result ->
                withContext(Dispatchers.Main) {
                    result.data?.let {
                        val gson = Gson()
                        val data =
                            gson.fromJson(result.data.response, LatestCurrencyRates::class.java)
                        latestCurrenciesRates = data
                    }
                    currenciesLatestRatesLiveData.value = Resource(
                        Resource.Status.SUCCESS,
                        result.data,
                        "successfully"
                    )
                }

            }
        }
    }

    private fun getCountriesList(response: CurrencyEntity) {
        val mapObject = ObjectMapper()
        val array = ArrayList<String>()
        val gson = Gson()
        val data = gson.fromJson(response.response, Currency::class.java)
        response.response.let {
            val mapObj: MutableMap<*, *>? =
                mapObject.convertValue(data, MutableMap::class.java)
            mapObj?.keys?.let {
                it.forEach { key ->
                    val value = key as String
                    array.add(value.uppercase())
                }
                list.value = array

            }
        }
    }


    fun getConvertedCurrency(currencyValue: Double, selectedCurrency: String): List<CurrencyItem> {
        val listCurreincies = mutableListOf<CurrencyItem>()
        var selectedCurrencyPrice = 00.00
        val df = DecimalFormat("#.##")
        if (currencyValue != null) {
            val selectedItem = latestCurrenciesRates.rates.currencies.find { value ->
                value.first == selectedCurrency
            }
            selectedCurrencyPrice = currencyValue * selectedItem?.second?.toDouble()!!
            currenciesList.value?.forEach { key ->
                val currencyItem = latestCurrenciesRates.rates.currencies.find { value ->
                    value.first == key
                }
                currencyItem?.let {
                    val price = currencyValue * currencyItem?.second.toDouble()
                    val item = CurrencyItem(key = key, value = df.format(price).toDouble())
                    listCurreincies.add(item)
                }
            }
        }
        selected_Country_Currency.value = df.format(selectedCurrencyPrice).toDouble()
        return listCurreincies
    }
}