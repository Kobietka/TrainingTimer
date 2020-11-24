package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.data.ActiveGoal
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ActiveGoalRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject


class CreateGoalUIViewModel
@Inject constructor(private val activeGoalRepository: ActiveGoalRepository,
                    private val workoutRepository: WorkoutRepository){

    val compositeDisposable = CompositeDisposable()
    private val workoutIds = BehaviorSubject.create<Int>().toSerialized()
    var id: Int? = 0

    private val _attachedWorkoutName = MutableLiveData<String>()
    private val _removeWorkout = MutableLiveData<Boolean>()

    init {
        compositeDisposable.add(
            workoutIds.subscribe(this::loadWorkout)
        )
    }

    fun attachedWorkoutName(): LiveData<String> {
        return _attachedWorkoutName
    }

    fun removeWorkout(): LiveData<Boolean> {
        return _removeWorkout
    }

    fun setCurrentWorkout(workoutId: Int){
        workoutIds.onNext(workoutId)
        _removeWorkout.value = true
        id = workoutId
    }

    fun onWorkoutRemoved(){
        _removeWorkout.value = false
        _attachedWorkoutName.value = "Attach to workout (optional)"
        id = 0
    }

    fun saveGoal(name: String, goal: Int, measurementType: MeasurementType){
        var isAttached = true
        if(id == 0) {
            isAttached = false
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

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _attachedWorkoutName.value = it.name
                }
        )
    }

}