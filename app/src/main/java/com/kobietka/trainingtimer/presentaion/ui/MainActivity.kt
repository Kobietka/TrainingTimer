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
import com.kobietka.trainingtimer.presentaion.common.BaseActivity
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddworkout.WorkoutAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise.ChooseExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenteditexercise.EditExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenteditworkout.EditWorkoutFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var launchEvents: Observable<EventType>
    private val compositeDisposable = CompositeDisposable()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presentationComponent.inject(this)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        compositeDisposable.add(
            launchEvents.subscribe(this::launchEvents)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun launchEvents(eventType: EventType){
        if(eventType.clickId == ClickId.EditExercise){
            navController.navigate(R.id.action_exercisesFragment_to_editExerciseFragment)
        } else if(eventType.clickId == ClickId.EditWorkout){
            navController.navigate(R.id.action_workoutsFragment_to_editWorkoutFragment)
        } else if(eventType.clickId == ClickId.AddExercise) {
            navController.navigate(R.id.action_editWorkoutFragment_to_chooseExerciseFragment)
        } else if(eventType.clickId == ClickId.EditWorkoutFromChoosing){
           navController.navigate(R.id.action_chooseExerciseFragment_to_editWorkoutFragment)
        } else if(eventType.clickId == ClickId.Play) {
            TODO("Waiting for timer fragment to be created")
        }
    }

}
