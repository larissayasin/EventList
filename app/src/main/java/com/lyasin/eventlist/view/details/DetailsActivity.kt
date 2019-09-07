package com.lyasin.eventlist.view.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

    private lateinit var event: Event

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
                        event = it.data
                        populateLayout()
                    }
                }
            }
        })

    }

    private fun populateLayout(){
        tv_details_title.text = event.title
        tv_details_description.text = event.description
        Glide.with(this).load(event.image).placeholder(R.drawable.lorem)
            .into(iv_details)

        tv_details_date.text = getString(R.string.when_date, event.convertedDate())
        tv_details_price.text = getString(R.string.price_value, event.priceCurrency())

        if (event.latitude.isEmpty() || event.longitude.isEmpty()){
            bt_details_location.visibility = View.GONE
        }else {
            bt_details_location.setOnClickListener {
                val uri = "geo:${event.latitude},${event.longitude}"
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(uri)
                    )
                )
            }
        }
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
               val shareBodyText = event.title
               sharingIntent.putExtra(Intent.EXTRA_SUBJECT, event.description)
               sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
               startActivity(Intent.createChooser(sharingIntent, "Shearing Option"))
               true
           }

           else -> super.onOptionsItemSelected(item)
       }
    }
}
