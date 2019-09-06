package com.lyasin.eventlist.api

import com.lyasin.eventlist.model.Event
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface EventApi {
    @GET("events")
    fun getEvents() : Observable<MutableList<Event>>

    @GET("events/{id}")
    fun getEventById(@Path("id") id : String) : Observable<Event>
}