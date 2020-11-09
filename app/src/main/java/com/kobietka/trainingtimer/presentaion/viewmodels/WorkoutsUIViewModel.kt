package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WorkoutsUIViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val workoutRelationRepository: WorkoutRelationRepository) {

    val compositeDisposable = CompositeDisposable()
    private val _textIfNoWorkouts = MutableLiveData<Boolean>()

    fun textIfNoWorkouts(): LiveData<Boolean> {
        return _textIfNoWorkouts
    }

    fun deleteWorkout(workoutId: Int){
        workoutRepository.deleteById(workoutId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        workoutRelationRepository.deleteByWorkoutId(workoutId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun loadWorkouts(){
        compositeDisposable.add(
            workoutRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _textIfNoWorkouts.value = it.isEmpty()
                }
        )
    }

}