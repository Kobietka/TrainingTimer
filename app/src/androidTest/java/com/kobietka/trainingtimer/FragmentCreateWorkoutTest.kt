package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.kobietka.trainingtimer.presentaion.ui.fragments.WorkoutAddFragment
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith


@RunWith(JUnit4ClassRunner::class)
class FragmentCreateWorkoutTest {

    @Test
    fun isBackArrowVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_back_arrow))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isAddButtonVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_add))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isWorkoutsTextVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isWorkoutsTextCorrect(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_text))
            .check(matches(withText(R.string.fragment_create_workout_text)))
    }

    @Test
    fun isEditIconVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_edit_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isNameEditTextVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_edit_text_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isTimerIconVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_timer_icon))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isRestEditTextVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_rest_edit_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isExercisesTextVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_exercises_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun isExercisesTextCorrect(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_exercises_text))
            .check(matches(withText(R.string.fragment_main_exercises_text)))
    }

    @Test
    fun isAddExerciseButtonVisible(){
        val scenario = launchFragmentInContainer<WorkoutAddFragment>()
        onView(withId(R.id.fragment_create_workout_add_exercise))
            .check(matches(isDisplayed()))
    }

}