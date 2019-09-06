package com.lyasin.eventlist.di

import com.lyasin.eventlist.repository.EventRepository
import com.lyasin.eventlist.repository.EventRepositoryImpl
import com.lyasin.eventlist.view.EventViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val eventModule = module {

    viewModel { EventViewModel(get()) }

    single<EventRepository>(createdAtStart = true) { EventRepositoryImpl(get()) }
}

val eventApp = listOf(remoteModule, eventModule)