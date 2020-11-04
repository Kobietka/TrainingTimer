package com.kobietka.trainingtimer.presentaion.viewmodels

import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class ChooseExerciseUIViewModel
@Inject constructor(private val launchEvents: Subject<EventType>){


    private val workoutIds = BehaviorSubject.create<Int>().toSerialized()
    private val backClicks = BehaviorSubject.create<ClickId>().toSerialized()

    init {
        backClicks.withLatestFrom(workoutIds, { clickId, workoutId ->
            launchEvents.onNext(EventType(clickId, workoutId))
        }).subscribe()
    }

    fun onBackPressed(){
        backClicks.onNext(ClickId.EditWorkoutFromChoosing)
    }

    fun switchWorkoutId(id: Int){
        workoutIds.onNext(id)
    }

}