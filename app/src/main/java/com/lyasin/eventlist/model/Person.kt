package com.lyasin.eventlist.model

data class Person(
    val id: String,
    val eventId: String,
    val name: String = "",
    val picture: String = ""
)