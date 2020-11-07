package com.kobietka.trainingtimer.presentaion.viewmodels

import com.kobietka.trainingtimer.repositories.ExerciseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExercisesUIViewModel
@Inject constructor(private val exerciseRepository: ExerciseRepository){


    fun deleteExercise(id: Int){
        exerciseRepository.deleteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}