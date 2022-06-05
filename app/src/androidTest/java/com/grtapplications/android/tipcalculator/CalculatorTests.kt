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
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service))
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$10.00"))))
    }

    @Test
    fun calculate_18_percent_tip() {
        onView(withId(R.id.cost_of_service))
            .perform(clearText(), typeText("100.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$18.00"))))
    }

    @Test
    fun calculate_15_percent_tip() {
        onView(withId(R.id.cost_of_service))
            .perform(clearText(),typeText("75.00"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$11.25"))))
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.grtapplications.android.tipcalculator", appContext.packageName)
    }
}