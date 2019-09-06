package com.lyasin.eventlist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.lyasin.eventlist.R
import org.koin.android.viewmodel.ext.android.viewModel

class EventsActivity : AppCompatActivity() {

    private val vm : EventViewModel by viewModel ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        vm.live.observe(this, Observer {
            when{
                it.isLoading -> Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                it.error != null -> Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
