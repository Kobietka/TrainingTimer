package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class TrainViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val relationRepository: WorkoutRelationRepository){

    private val compositeDisposable = CompositeDisposable()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _numberOfExercises = MutableLiveData<Int>()

    fun name(): LiveData<String> = _name
    fun numberOfExercises(): LiveData<Int> = _numberOfExercises

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadWorkout)
        )
    }

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
            relationRepository.getExercisesByWorkoutId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _numberOfExercises.value = it.size
                }
        )
    }


}