package com.lyasin.eventlist.view.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.repository.EventRepository
import com.lyasin.eventlist.util.LiveUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    val live = MutableLiveData<LiveUtil<MutableList<Event>>>()

    fun listEvents() {
        live.value = LiveUtil(isLoading = true)
        val d = repository.getEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                live.value = LiveUtil(data = it, isSuccess = true)
            }, { error ->
                live.value = LiveUtil(error = error)
            })
    }
}