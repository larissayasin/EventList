package com.lyasin.eventlist

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.lyasin.eventlist.view.events.EventsActivity
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class EventInstrumentedTest {
    @get:Rule
    var activityTestRule: ActivityTestRule<EventsActivity> =
        ActivityTestRule(EventsActivity::class.java)

    @Test
    fun testSelecItemAndOpenDetails(){
        Thread.sleep(100)
        onView(withText(R.string.loading)).inRoot(ToastHelper())
            .check(matches(isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.rv_events))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(hasDescendant(withText("Feira de adoção de animais na Redenção")), click())
            )

        Thread.sleep(500)

        onView(withText(R.string.loading)).inRoot(ToastHelper())
            .check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withText(containsString("Patas Dadas"))).check(matches(isDisplayed()))


    }


}
