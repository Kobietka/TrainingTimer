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


class EditGoalUIViewModel
@Inject constructor(private val activeGoalRepository: ActiveGoalRepository,
                    private val workoutRepository: WorkoutRepository){

    val compositeDisposable = CompositeDisposable()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _goalValue = MutableLiveData<String>()
    private val _goalType = MutableLiveData<MeasurementType>()
    private val _attachedWorkout = MutableLiveData<String>()

    var currentWorkoutId: Int? = 0
    var currentGoalId = 0

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadGoal)
        )
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun goalValue(): LiveData<String> {
        return _goalValue
    }

    fun goalType(): LiveData<MeasurementType> {
        return _goalType
    }

    fun attachedWorkout(): LiveData<String> {
        return _attachedWorkout
    }

    fun switchGoalId(id: Int){
        ids.onNext(id)
        currentGoalId = id
    }

    fun setCurrentWorkout(id: Int){
        loadWorkout(id)
        currentWorkoutId = id
    }

    private fun loadGoal(id: Int){
        compositeDisposable.add(
            activeGoalRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                    _goalValue.value = it.goal.toString()
                    _goalType.value = it.type
                    if(!it.isAttached) _attachedWorkout.value = "Attach to workout (optional)"
                    else loadWorkout(it.workoutId!!)
                }
        )
    }

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _attachedWorkout.value = it.name
                }
        )
    }

    fun saveGoal(goalName: String, goalValue: Int, type: MeasurementType) {
        if(currentWorkoutId != 0) {
            compositeDisposable.add(
                activeGoalRepository.updateWorkoutId(currentWorkoutId, currentGoalId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }

        compositeDisposable.add(
            activeGoalRepository.updateName(goalName, currentGoalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        compositeDisposable.add(
            activeGoalRepository.updateGoal(goalValue, currentGoalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        compositeDisposable.add(
            activeGoalRepository.updateType(type, currentGoalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

    }

}