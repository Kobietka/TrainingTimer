package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.kobietka.trainingtimer.presentaion.ui.MainActivity
import com.kobietka.trainingtimer.presentaion.ui.fragments.WorkoutsFragment
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith


@RunWith(JUnit4ClassRunner::class)
class FragmentWorkoutsTest {

    @Test
    fun isBackArrowVisible(){
        val scenario = launchFragmentInContainer<WorkoutsFragment>()
        onView(withId(R.id.fragment_workouts_back_arrow))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isAddIconVisible(){
        val scenario = launchFragmentInContainer<WorkoutsFragment>()
        onView(withId(R.id.fragment_workouts_add))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isTextCorrect(){
        val scenario = launchFragmentInContainer<WorkoutsFragment>()
        onView(withId(R.id.fragment_workouts_text))
            .check(matches(withText(R.string.fragment_main_workouts_text)))
    }

    @Test
    fun isTextVisible(){
        val scenario = launchFragmentInContainer<WorkoutsFragment>()
        onView(withId(R.id.fragment_workouts_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isRecyclerViewVisible(){
        val scenario = launchFragmentInContainer<WorkoutsFragment>()
        onView(withId(R.id.fragment_workouts_rv))
            .check(matches(isDisplayed()))
    }

    @Test
    fun backArrowNavigation(){
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.fragment_main_workouts_element)).perform(click())

        onView(withId(R.id.fragment_workouts))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fragment_workouts_back_arrow)).perform(click())

        onView(withId(R.id.fragment_main))
            .check(matches(isDisplayed()))
    }


}