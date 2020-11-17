package com.kobietka.trainingtimer.presentaion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.WorkoutAddExerciseEvent
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddworkout.WorkoutAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise.ChooseExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenteditexercise.EditExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenteditworkout.EditWorkoutFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
