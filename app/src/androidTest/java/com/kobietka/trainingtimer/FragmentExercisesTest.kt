package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kobietka.trainingtimer.presentaion.ui.MainActivity
import com.kobietka.trainingtimer.presentaion.ui.fragmentexercises.ExercisesFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class FragmentExercisesTest {

    @Before
    fun initScenario(){
        val scenario = launchFragmentInContainer<ExercisesFragment>()
    }

    @Test
    fun isBackArrowVisible(){
        onView(withId(R.id.fragment_exercises_back_arrow))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isAddButtonVisible(){
        onView(withId(R.id.fragment_exercises_add))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isExercisesTextCorrect(){
        onView(withId(R.id.fragment_exercises_text))
            .check(matches(withText(R.string.fragment_main_exercises_text)))
    }

    @Test
    fun isExercisesTextVisible(){
        onView(withId(R.id.fragment_exercises_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun backArrowNavigationTest(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.fragment_main_exercises_element)).perform(click())

        onView(withId(R.id.fragment_exercises))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fragment_exercises_back_arrow)).perform(click())

        onView(withId(R.id.fragment_main))
            .check(matches(isDisplayed()))
    }



}




