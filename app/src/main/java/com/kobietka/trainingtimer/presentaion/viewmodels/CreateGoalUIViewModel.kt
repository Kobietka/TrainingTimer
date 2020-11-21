package com.kobietka.trainingtimer.presentaion.viewmodels

import com.kobietka.trainingtimer.data.ActiveGoal
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ActiveGoalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class CreateGoalUIViewModel
@Inject constructor(private val activeGoalRepository: ActiveGoalRepository){

    val compositeDisposable = CompositeDisposable()
    var id: Int? = 0

    fun setCurrentWorkout(workoutId: Int){
        id = workoutId
    }

    fun saveGoal(name: String, goal: Int, measurementType: MeasurementType){
        var isAttached = false
        if(id != 0) {
            isAttached = true
            id = null
        }

        val calendar = Calendar.getInstance()
        val creationDate = calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                calendar.get(Calendar.MONTH).toString() + "/" +
                calendar.get(Calendar.YEAR).toString()

        compositeDisposable.add(
            activeGoalRepository.insert(
                ActiveGoal(null, name, measurementType, goal, 0, isAttached, id, creationDate)
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}