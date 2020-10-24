package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.WorkoutDao
import com.kobietka.trainingtimer.data.WorkoutEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject


class WorkoutRepository
@Inject constructor(private val workoutDao: WorkoutDao){

    fun getAllWorkouts(): Maybe<List<WorkoutEntity>> {
        return workoutDao.getAllWorkouts()
    }

    fun insertWorkout(workoutEntity: WorkoutEntity){
        workoutDao.insertWorkout(workoutEntity)
    }

    fun getById(id: Int): Observable<WorkoutEntity> {
        return workoutDao.getById(id)
    }

    fun deleteById(id: Int): Completable {
        return workoutDao.deleteById(id)
    }

}