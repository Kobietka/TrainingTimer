package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kobietka.trainingtimer.presentaion.ui.fragments.MainFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class FragmentMainTest {

    @Before
    fun initScenario(){
        val scenario = launchFragmentInContainer<MainFragment>()
    }

    @Test
    fun isAppNameCorrect(){
        onView(withId(R.id.fragment_main_app_name))
            .check(matches(withText(R.string.app_name)))
    }

    @Test
    fun isTrainTextCorrect(){
        onView(withId(R.id.fragment_main_train_text))
            .check(matches(withText(R.string.fragment_main_train_text)))
    }

    @Test
    fun isWorkoutsTextCorrect(){
        onView(withId(R.id.fragment_main_workouts_text))
            .check(matches(withText(R.string.fragment_main_workouts_text)))
    }

    @Test
    fun isExercisesTextCorrect(){
        onView(withId(R.id.fragment_main_exercises_text))
            .check(matches(withText(R.string.fragment_main_exercises_text)))
    }


    /*@Test
    fun isStatisticsTextCorrect(){
        onView(withId(R.id.fragment_main_statistics_text_view))
            .check(matches(withText(R.string.fragment_main_statistics_text)))
    }*/


}