package com.lyasin.eventlist.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.repository.EventRepository
import com.lyasin.eventlist.util.LiveUtil

class EventViewModel (val repository: EventRepository) : ViewModel(){

    val live = MutableLiveData<LiveUtil<Event>>()

    fun listEvents(){
        live.value = LiveUtil(isLoading = true)
    }
}