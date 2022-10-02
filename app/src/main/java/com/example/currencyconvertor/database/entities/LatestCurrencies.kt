package com.example.currencyconvertor.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latestCurrency")
data class LatestCurrencies(
    @PrimaryKey()
    @ColumnInfo(name="id")
    val id : Int? = 0,
    @ColumnInfo(name="response")
    val response : String
)
