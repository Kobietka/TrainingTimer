package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kobietka.trainingtimer.presentaion.ui.MainActivity
import com.kobietka.trainingtimer.presentaion.ui.fragments.ExerciseAddFragment
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith


@RunWith(JUnit4ClassRunner::class)

class FragmentAddExerciseTest {

    @Test
    fun testBackArrowNavigation(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.fragment_main_exercises_element)).perform(click())

        onView(withId(R.id.fragment_exercises))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fragment_exercises_add)).perform(click())

        onView(withId(R.id.fragment_create_exercise))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fragment_create_exercises_back_arrow)).perform(click())

        onView(withId(R.id.fragment_exercises))
            .check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun isBackArrowVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercises_back_arrow))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isBarTextVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercises_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isEditIconVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercise_edit_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isNameTextFieldVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercise_edit_text_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isShortTextIconVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercise_short_text_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isMeasurementTextVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercise_measurement_text_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isRadioGroupVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercise_radio_group))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isTimerIconVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercise_timer_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isCountTextFieldVisible(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
        onView(withId(R.id.fragment_create_exercise_count_edit_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCreateExerciseButton_NoDataFilled(){
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.fragment_main_exercises_element)).perform(click())

        onView(withId(R.id.fragment_exercises))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fragment_exercises_add)).perform(click())

        onView(withId(R.id.fragment_create_exercise))
            .check(matches(isDisplayed()))

        onView(withId(R.id.fragment_create_exercises_add)).perform(click())

        onView(withId(R.id.fragment_create_exercise))
            .check(matches(isDisplayed()))

        activityScenario.close()
    }

}