package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith


@RunWith(JUnit4ClassRunner::class)
class FragmentAddExerciseTest {

    @Before
    fun init(){
        val scenario = launchFragmentInContainer<ExerciseAddFragment>()
    }

    @Test
    fun isBackArrowVisible(){
        onView(withId(R.id.fragment_create_exercises_back_arrow))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isBarTextVisible(){
        onView(withId(R.id.fragment_create_exercises_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isEditIconVisible(){
        onView(withId(R.id.fragment_create_exercise_edit_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isNameTextFieldVisible(){
        onView(withId(R.id.fragment_create_exercise_edit_text_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isShortTextIconVisible(){
        onView(withId(R.id.fragment_create_exercise_short_text_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isMeasurementTextVisible(){
        onView(withId(R.id.fragment_create_exercise_measurement_text_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isRadioGroupVisible(){
        onView(withId(R.id.fragment_create_exercise_radio_group))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isTimerIconVisible(){
        onView(withId(R.id.fragment_create_exercise_timer_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isCountTextFieldVisible(){
        onView(withId(R.id.fragment_create_exercise_count_edit_text))
            .check(matches(isDisplayed()))
    }

}