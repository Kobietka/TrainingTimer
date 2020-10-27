package com.kobietka.trainingtimer.presentaion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.presentaion.common.BaseActivity
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var launchEvents: Subject<EventType>
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
        if(eventType.clickId == 1){
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ExerciseAddFragment())
                .commit()
        }
    }

}
