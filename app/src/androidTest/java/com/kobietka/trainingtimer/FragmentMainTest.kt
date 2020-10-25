package com.kobietka.trainingtimer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
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
        onView(withId(R.id.fragment_main_train_text_view))
            .check(matches(withText(R.string.fragment_main_train_text)))
    }

    @Test
    fun isTrainDescriptionCorrect(){
        onView(withId(R.id.fragment_main_train_description_text_view))
            .check(matches(withText(R.string.fragment_main_train_description)))
    }

    @Test
    fun isWorkoutsTextCorrect(){
        onView(withId(R.id.fragment_main_workouts_text_view))
            .check(matches(withText(R.string.fragment_main_workouts_text)))
    }

    @Test
    fun isWorkoutsDescriptionCorrect(){
        onView(withId(R.id.fragment_main_workouts_description_text_view))
            .check(matches(withText(R.string.fragment_main_workouts_description)))
    }

    @Test
    fun isExercisesTextCorrect(){
        onView(withId(R.id.fragment_main_exercises_text_view))
            .check(matches(withText(R.string.fragment_main_exercises_text)))
    }

    @Test
    fun isExercisesDescriptionCorrect(){
        onView(withId(R.id.fragment_main_exercises_description_text_view))
            .check(matches(withText(R.string.fragment_main_exercises_description)))
    }

    @Test
    fun isStatisticsTextCorrect(){
        onView(withId(R.id.fragment_main_statistics_text_view))
            .check(matches(withText(R.string.fragment_main_statistics_text)))
    }

    @Test
    fun isStatisticsDescriptionCorrect(){
        onView(withId(R.id.fragment_main_statistics_description_text_view))
            .check(matches(withText(R.string.fragment_main_statistics_description)))
    }

}