package com.grtapplications.android.tipcalculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matchers.containsString

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CalculatorTests {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service))
            .perform(clearText())
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.option_twenty_percent))
            .perform(click())
        onView(withId(R.id.calculate_button))
            .perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$10.00"))))
        onView(withId(R.id.total_result))
            .check(matches(withText(containsString("$60.00"))))
    }

    @Test
    fun calculate_18_percent_tip() {
        onView(withId(R.id.cost_of_service))
            .perform(clearText())
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.option_eighteen_percent))
            .perform(click())
        onView(withId(R.id.calculate_button))
            .perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$9.00"))))
        onView(withId(R.id.total_result))
            .check(matches(withText(containsString("$59.00"))))
    }

    @Test
    fun calculate_15_percent_tip() {
        onView(withId(R.id.cost_of_service))
            .perform(clearText())
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.option_fifteen_percent))
            .perform(click())
        onView(withId(R.id.calculate_button))
            .perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$7.50"))))
        onView(withId(R.id.total_result))
            .check(matches(withText(containsString("$57.50"))))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.grtapplications.android.tipcalculator", appContext.packageName)
    }
}