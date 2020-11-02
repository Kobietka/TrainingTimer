package com.kobietka.trainingtimer.presentaion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var launchEvents: Subject<EventType>
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presentationComponent.inject(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment(), "main_fragment")
            .commit()

        compositeDisposable.add(
            launchEvents.subscribe(this::launchEvents)
        )
    }

    private fun launchEvents(eventType: EventType){
        if(eventType.clickId == ClickId.EditExercise){
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, EditExerciseFragment())
                .commit()
        } else if(eventType.clickId == ClickId.EditWorkout){
            val fragment = supportFragmentManager.findFragmentByTag("edit_workout_fragment")
            if(fragment == null){
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, EditWorkoutFragment(), "edit_workout_fragment")
                    .commit()
            } else {
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment, "edit_workout_fragment")
                    .commit()
            }
        } else if(eventType.clickId == ClickId.AddExercise) {
            val fragment = supportFragmentManager.findFragmentByTag("add_exercise_fragment")
            if (fragment == null) {
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, ChooseExerciseFragment(), "add_exercise_fragment")
                    .commit()
            } else {
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment, "add_exercise_fragment")
                    .commit()
            }
        } else if(eventType.clickId == ClickId.EditWorkoutFromChoosing){
            val fragment = supportFragmentManager.findFragmentByTag("edit_workout_fragment")
            if(fragment == null){
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, EditWorkoutFragment(), "edit_workout_fragment")
                    .commit()
            } else {
                this.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment, "edit_workout_fragment")
                    .commit()
            }
        }
    }

}
