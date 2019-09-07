package com.lyasin.eventlist.repository

import com.lyasin.eventlist.api.EventApi
import com.lyasin.eventlist.model.Checkin
import com.lyasin.eventlist.model.Event
import io.reactivex.Observable

interface EventRepository {

    fun getEvents(): Observable<MutableList<Event>>
    fun getEvent(id: String): Observable<Event>
    fun checkin(id: String, name: String, email: String): Observable<Void>
}

class EventRepositoryImpl(private val service: EventApi) : EventRepository {
    override fun checkin(id: String, name: String, email: String): Observable<Void> =
        service.checkin(
            Checkin(id, name, email)
        )

    override fun getEvent(id: String): Observable<Event> = service.getEventById(id)

    override fun getEvents(): Observable<MutableList<Event>> = service.getEvents()

}
