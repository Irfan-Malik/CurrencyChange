package com.example.currencyconvertor.remote.model.currency_rates

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class Rates(
    val currencies: List<Pair<String,String>>
)

class RateDeserializer : JsonDeserializer<Rates> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Rates {
        val value = json.toString()
        val splits = value.split(",").toMutableList()
        splits[0] = splits.first().removeRange(0,1)
        splits[splits.size - 1] = splits.last().removeRange(splits.last().length - 2, splits.last().length - 1)
        val result = splits.map {
            val split = it.split(":")
            Pair(split.first().removeSurrounding("\"","\""),split.last().replace("}",""))
        }
        return Rates(result)
    }
}