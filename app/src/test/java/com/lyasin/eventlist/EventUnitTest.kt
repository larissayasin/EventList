package com.lyasin.eventlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.repository.EventRepository
import com.lyasin.eventlist.util.LiveUtil
import com.lyasin.eventlist.view.events.EventViewModel
import io.reactivex.Observable
import org.junit.*
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventUnitTest : KoinTest {

    private var eventId1 = "1"
    private var eventId2 = "2"

    lateinit var eventsViewModel: EventViewModel

    private val e1 = Event(id = eventId1)
    private val e2 = Event(id = eventId2)

    private val listEvents : MutableList<Event> = mutableListOf(e1, e2)
    @Mock
    lateinit var observer: Observer<LiveUtil<MutableList<Event>>>

    @Mock
    lateinit var repository: EventRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        eventsViewModel = EventViewModel(repository)
    }

    @Test
    fun testGetEventList() {
        Mockito.`when`(repository.getEvents()).thenReturn(Observable.just(listEvents))

        eventsViewModel.live.observeForever(observer)
        eventsViewModel.listEvents()
        Mockito.verify(observer).onChanged(LiveUtil(data = listEvents, isSuccess = true))
    }

    @Test
    fun testGetEventListError(){
        val erro = IllegalAccessError("Error")
        Mockito.`when`(repository.getEvents()).thenReturn(Observable.error(erro))

        eventsViewModel.live.observeForever(observer)
        eventsViewModel.listEvents()
        Mockito.verify(observer).onChanged(LiveUtil(error = erro))
    }

}