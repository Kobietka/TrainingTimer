package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenteditexercise.EditExerciseFragment
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith

@RunWith(JUnit4ClassRunner::class)
class FragmentEditExerciseTest {


    @Test
    fun isBackArrowVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercises_back_arrow))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isAddButtonVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercises_add))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isNameTextFieldVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercise_edit_text_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isEditIconVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercise_edit_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isRadioButtonGroupVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercise_radio_group))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isShortTextIconVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercise_short_text_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isCountTextFieldVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercise_count_edit_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isTimerIconVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercise_timer_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isTitleVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercises_text))
            .check(matches(withText(R.string.fragment_edit_exercise_text)))
    }

    @Test
    fun isMeasurementTextVisible(){
        val scenario = launchFragmentInContainer<EditExerciseFragment>()
        onView(withId(R.id.fragment_edit_exercise_measurement_text_view))
            .check(matches(withText(R.string.fragment_create_exercise_measurement_type_text)))
    }

}