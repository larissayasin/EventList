package com.lyasin.eventlist.model

data class Cupon(val id: String, val eventId: String, val discount: Int = 0) {
    fun discountPercent(): String {
        return if (discount > 0) {
            "$discount %"
        } else {
            "No cupon"
        }
    }
}