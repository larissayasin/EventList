package com.lyasin.eventlist.model

import com.lyasin.eventlist.api.ApiConfig
import com.squareup.moshi.Json
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

data class Event(
    @Json(name = "people") val people: MutableList<Person>? = null,
    @Json(name = "cupons") val cupons: MutableList<Cupon>? = null,
    val date: Long = 0L,
    val description: String = "",
    val image: String = "",
    val longitude: String = "",
    val latitude: String = "",
    val price: Double = 0.0,
    val title: String = "",
    val id: String
) {


    fun convertedDate(): String {
        val date = Date(date)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", ApiConfig.LOCALE)
        return format.format(date)
    }

    fun priceCurrency(): String = NumberFormat.getCurrencyInstance(ApiConfig.LOCALE).format(price)
}