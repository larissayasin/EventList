package com.lyasin.eventlist.repository

import com.lyasin.eventlist.api.EventApi
import com.lyasin.eventlist.model.Event
import io.reactivex.Observable

interface EventRepository {

    fun getEvents() : Observable<MutableList<Event>>
}
class EventRepositoryImpl (private val service : EventApi) : EventRepository{
    override fun getEvents(): Observable<MutableList<Event>> = service.getEvents()

}
