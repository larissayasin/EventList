package com.lyasin.eventlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lyasin.eventlist.model.Event
import com.lyasin.eventlist.repository.EventRepository
import com.lyasin.eventlist.util.LiveUtil
import com.lyasin.eventlist.view.details.DetailsViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.Assert.assertThat
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailsUnitTest : KoinTest {

    private var eventId1 = "1"

    lateinit var detailsViewModel: DetailsViewModel

    private val e1 = Event(id = eventId1)

    @Mock
    lateinit var observer: Observer<LiveUtil<Event>>

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
        detailsViewModel = DetailsViewModel(repository)
    }

    @Test
    fun testEventCurrency(){
        val e = Event(id = eventId1, price = 10.5)
        assertThat(e.priceCurrency(), `is`("R$ 10,50"))
    }

    @Test
    fun testEventCurrencyEmpty(){
        val e = Event(id = eventId1, price = 0.0)
        assertThat(e.priceCurrency(), `is`("R$ 0,00"))
    }

    @Test
    fun testEventDate(){
        val e = Event(id = eventId1, date = 1534784400000)
        assertThat(e.convertedDate(), `is`("20/08/2018 14:00"))
    }

    @Test
    fun testEventDateEmpty(){
        val e = Event(id = eventId1, date = 0)
        assertThat(e.convertedDate(), `is`("not specified"))
    }

    @Test
    fun testGetEvent() {
        Mockito.`when`(repository.getEvent(eventId1)).thenReturn(Observable.just(e1))

        detailsViewModel.live.observeForever(observer)
        detailsViewModel.getEvent(eventId1)
        Mockito.verify(observer).onChanged(LiveUtil(data = e1, isSuccess = true))
    }


    @Test
    fun testCheckinEvent() {
        Mockito.`when`(repository.checkin(eventId1, "a", "a@a.com")).thenReturn(Observable.empty())

        detailsViewModel.live.observeForever(observer)
        detailsViewModel.checkin(eventId1, "a", "a@a.com")
        Mockito.verify(observer).onChanged(LiveUtil(isSuccess = true))
    }

    @Test
    fun testGetEventError(){
        val erro = IllegalAccessError("Error")
        Mockito.`when`(repository.getEvent(eventId1)).thenReturn(Observable.error(erro))

        detailsViewModel.live.observeForever(observer)
        detailsViewModel.getEvent(eventId1)
        Mockito.verify(observer).onChanged(LiveUtil(error = erro))
    }

}