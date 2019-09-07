package com.lyasin.eventlist.view.details

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.lyasin.eventlist.R
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.view.INTENT_EXTRA_ID
import com.lyasin.eventlist.view.errorSnackbar
import com.lyasin.eventlist.view.loadingToast
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailsActivity : AppCompatActivity() {

    private val vm : DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val id = intent.getStringExtra(INTENT_EXTRA_ID)
        vm.getEvent(id ?: "")

        vm.live.observe(this, Observer {
            when{
                it.isLoading -> loadingToast(this)
                it.error != null -> errorSnackbar(this)
                it.isSuccess -> {
                    if (it.data != null) {
                        populateLayout(it.data)
                    }
                }
            }
        })

    }

    private fun populateLayout(e : Event){
        tv_details_title.text = e.title
        tv_details_description.text = e.description
        Glide.with(this).load(e.image).placeholder(R.drawable.lorem)
            .into(iv_details)
    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
           R.id.menu_share -> {
               val sharingIntent = Intent(Intent.ACTION_SEND)
               sharingIntent.type = "text/plain"
               val shareBodyText = "Check it out. Your message goes here"
               sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here")
               sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
               startActivity(Intent.createChooser(sharingIntent, "Shearing Option"))
               true
           }

           else -> super.onOptionsItemSelected(item)
       }
    }
}
