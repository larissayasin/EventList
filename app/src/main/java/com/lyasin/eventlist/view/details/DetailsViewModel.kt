package com.lyasin.eventlist.view.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.repository.EventRepository
import com.lyasin.eventlist.util.LiveUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val repository: EventRepository) : ViewModel() {

    val live = MutableLiveData<LiveUtil<Event>>()

    fun getEvent(id: String) {
        live.value = LiveUtil(isLoading = true)
        val d = repository.getEvent(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                live.value = LiveUtil(data = it, isSuccess = true)
            }, { error ->
                live.value = LiveUtil(error = error)
            })

    }
    fun checkin(id: String, name: String, email: String){
        live.value = LiveUtil(isLoading = true)
        val d = repository.checkin(id, name, email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                live.value = LiveUtil(isSuccess = true)
            }, { error ->
                live.value = LiveUtil(error = error)
            })
    }
}