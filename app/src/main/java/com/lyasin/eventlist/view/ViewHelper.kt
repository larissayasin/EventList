package com.lyasin.eventlist.view

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.lyasin.eventlist.R

const val INTENT_EXTRA_ID = "id"

fun loadingToast( ctx : Context){
    Toast.makeText(
        ctx,
        ctx.getString(R.string.loading),
        Toast.LENGTH_SHORT
    ).show()
}

fun errorSnackbar(activity: Activity){
    Snackbar.make(activity.findViewById(android.R.id.content),
        activity.getString(R.string.error),
        Snackbar.LENGTH_SHORT).show()
}

fun checkinDoneSnackbar(activity: Activity){
    Snackbar.make(activity.findViewById(android.R.id.content),
        activity.getString(R.string.checkin_done),
        Snackbar.LENGTH_SHORT).show()
}