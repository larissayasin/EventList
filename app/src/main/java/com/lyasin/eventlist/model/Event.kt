package com.lyasin.eventlist.model

import com.squareup.moshi.Json
import java.text.SimpleDateFormat
import java.util.*

data class Event(
    @Json(name = "people") val people: MutableList<Person>? = null,
    @Json(name = "cupons") val cupons: MutableList<Cupon>? = null,
    val date : Long = 0L,
    val description : String = "",
    val image : String = "",
    val longitude : String = "",
    val latitude : String = "",
    val price : Double = 0.0,
    val title : String = "",
    val id : String
){
    fun Long.convertToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/YYYY HH:mm", Locale.ENGLISH)
        return format.format(date)
    }
}