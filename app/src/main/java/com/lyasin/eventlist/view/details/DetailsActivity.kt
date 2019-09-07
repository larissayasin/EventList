package com.lyasin.eventlist.view.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.lyasin.eventlist.R
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.view.INTENT_EXTRA_ID
import com.lyasin.eventlist.view.checkinDoneSnackbar
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
                    }else{
                        checkinDoneSnackbar(this)
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

        val cupons = event.cupons?.map { it.discountPercent() }
        cupons?.let {
            tv_details_cupons.text = it.joinToString()
        }
        val people = event.people?.map { it.name }
        people?.let {
            tv_details_people.text = it.joinToString()
        }

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
        bt_details_checkin.setOnClickListener {
            MaterialDialog(this, BottomSheet()).show {
                title(R.string.check_in_title)
                customView(R.layout.view_dialog_checkin)
                positiveButton(R.string.check_in){dialog ->
                    val nameInput: EditText = dialog.getCustomView()
                        .findViewById(R.id.et_dialog_checkin_name)
                    val emailInput: EditText = dialog.getCustomView()
                        .findViewById(R.id.et_dialog_checkin_email)
                    when{
                        nameInput.text.isNullOrEmpty() -> (dialog.customView().findViewById(R.id.til_dialog_checkin_name) as TextInputLayout).error = getString(
                                                    R.string.field_required)
                        emailInput.text.isNullOrEmpty() -> (dialog.customView().findViewById(R.id.til_dialog_checkin_email) as TextInputLayout).error = getString(
                            R.string.field_required)
                        else-> doCheckin(nameInput.text.toString(), emailInput.text.toString())
                    }
                }

            }
        }
    }

    private fun doCheckin(name : String, email: String){
        vm.checkin(event.id, name, email)
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
