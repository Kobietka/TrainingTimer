package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class TrainUIViewModel
@Inject constructor(private val workoutsRepository: WorkoutRepository) {

    private val compositeDisposable = CompositeDisposable()

    private val _ifNoExercises = MutableLiveData<Boolean>()

    fun ifNoExercises(): LiveData<Boolean> {
        return _ifNoExercises
    }

    fun checkWorkouts(){
        loadWorkouts()
    }

    fun loadWorkouts(){
        compositeDisposable.add(
            workoutsRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _ifNoExercises.value = it.isEmpty()
                }
        )
    }

}