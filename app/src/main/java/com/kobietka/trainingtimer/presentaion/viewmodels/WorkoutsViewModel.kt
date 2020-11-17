package com.kobietka.trainingtimer.presentaion.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class WorkoutsViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val relationsRepository: WorkoutRelationRepository){

    private val compositeDisposable = CompositeDisposable()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _numberOfExercises = MutableLiveData<Int>()

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadWorkout)
        )
    }

    fun name(): LiveData<String> = _name
    fun numberOfExercises(): LiveData<Int> = _numberOfExercises

    fun switchId(id: Int){
        ids.onNext(id)
    }

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                }
        )

        compositeDisposable.add(
            relationsRepository.getExercisesByWorkoutId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _numberOfExercises.value = it.size
                }
        )
    }

}