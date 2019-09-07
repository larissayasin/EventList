package com.lyasin.eventlist.view.events

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lyasin.eventlist.R
import com.lyasin.eventlist.view.errorSnackbar
import com.lyasin.eventlist.view.loadingToast
import kotlinx.android.synthetic.main.activity_events.*
import org.koin.android.viewmodel.ext.android.viewModel

class EventsActivity : AppCompatActivity() {

    private val vm: EventViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        vm.listEvents()

        vm.live.observe(this, Observer {
            when {
                it.isLoading -> loadingToast(this)
                it.isSuccess -> {
                    val viewManager = androidx.recyclerview.widget.LinearLayoutManager(this)
                    val viewAdapter = EventAdapter(it.data ?: mutableListOf())
                    rv_events.apply {
                        setHasFixedSize(true)
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                }
                it.error != null -> errorSnackbar(this)
            }
        })
    }
}
