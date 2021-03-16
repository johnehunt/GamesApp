package com.jjh.games

import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
                  ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testThatDrawerIsInitialClosed() {
        currentActivity.run {
          Espresso
            .onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(
                      DrawerMatchers.isClosed()))
        }
    }

    @Test
    fun testTheDrawerOpens() {
      currentActivity.run {
        Espresso
          .onView(ViewMatchers.withId(R.id.drawer_layout))
          .perform(DrawerActions.open())
          .check(ViewAssertions.matches(
            DrawerMatchers.isOpen()))
      }
    }

    private val currentActivity: MainActivity
        get() {
           var activity: MainActivity? = null
           activityRule.scenario.onActivity {
               activity = it
           }
           return activity!!
        }

}
