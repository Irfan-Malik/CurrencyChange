package com.example.currencyconvertor.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val name : Int? = 0,
    @ColumnInfo(name="response")
    val response:String
)
