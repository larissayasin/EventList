package com.lyasin.eventlist

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastHelper : TypeSafeMatcher<Root>() {
    override fun matchesSafely(root: Root): Boolean {
        val type = root.windowLayoutParams.get().type
        if ((type == WindowManager.LayoutParams.TYPE_TOAST))
        {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken
            if (windowToken === appToken)
            {
                return true
            }
        }
        return false
    }

    override fun describeTo(description: Description?) {
        description?.appendText("is toast")
    }
}