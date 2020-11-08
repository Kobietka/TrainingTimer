package com.kobietka.trainingtimer.presentaion.viewmodels

import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WorkoutsUIViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val workoutRelationRepository: WorkoutRelationRepository) {


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


}