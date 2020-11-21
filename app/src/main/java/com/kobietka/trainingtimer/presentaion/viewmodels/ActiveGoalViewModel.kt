package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ActiveGoalRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class ActiveGoalViewModel
@Inject constructor(private val activeGoalRepository: ActiveGoalRepository,
                    private val workoutRepository: WorkoutRepository){

    private val compositeDisposable = CompositeDisposable()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name =  MutableLiveData<String>()
    private val _type = MutableLiveData<String>()
    private val _attached = MutableLiveData<String>()
    private val _goal = MutableLiveData<String>()
    private val _currentProgress = MutableLiveData<String>()

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadGoal)
        )
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun type(): LiveData<String> {
        return _type
    }

    fun attached(): LiveData<String> {
        return _attached
    }

    fun goal(): LiveData<String> {
        return _goal
    }

    fun currentProgress(): LiveData<String> {
        return _currentProgress
    }

    fun switchId(id: Int){
        ids.onNext(id)
    }

    private fun loadGoal(id: Int){
        compositeDisposable.add(
            activeGoalRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                    _type.value = when(it.type){
                        MeasurementType.Time -> "Type: Time"
                        MeasurementType.Repetition -> "Type: Repetition"
                    }
                    _goal.value = it.goal.toString()
                    _currentProgress.value = it.currentProgress.toString()
                    if(it.workoutId == null){
                        _attached.value = "Overall"
                    } else loadWorkout(it.workoutId)
                }
        )
    }

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _attached.value = "Attached to: ${it.name}"
                }
        )
    }

}