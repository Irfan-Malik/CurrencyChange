package com.example.currencyconvertor.di

import com.example.currencyconvertor.remote.model.currency_rates.RateDeserializer
import com.example.currencyconvertor.remote.model.currency_rates.Rates
import com.example.currencyconvertor.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient().newBuilder()
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            ).build()
    }

@Singleton
@Provides
fun provideRetrofit(client: OkHttpClient):Retrofit{
    val gson = GsonBuilder()
        .registerTypeAdapter(Rates::class.java, RateDeserializer())
        .create()
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

}