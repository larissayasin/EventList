package com.lyasin.eventlist

import android.app.Application
import com.lyasin.eventlist.di.eventApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class EventApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@EventApplication)
            modules(eventApp)
        }
    }
}