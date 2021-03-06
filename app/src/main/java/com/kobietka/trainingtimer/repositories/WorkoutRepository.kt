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

    fun insertWorkout(workoutEntity: WorkoutEntity): Maybe<Long> {
        return workoutDao.insertWorkout(workoutEntity)
    }

    fun getById(id: Int): Maybe<WorkoutEntity> {
        return workoutDao.getById(id)
    }

    fun deleteById(id: Int): Completable {
        return workoutDao.deleteById(id)
    }

    fun deleteAllWorkouts(): Completable {
        return workoutDao.deleteAllWorkouts()
    }

    fun getAllIds(): Observable<List<Int>> {
        return workoutDao.getAllIds()
    }

    fun updateName(id: Int, name: String): Completable {
        return workoutDao.updateName(id, name)
    }

    fun updateRestTime(id: Int, restTime: Int): Completable {
        return workoutDao.updateRestTime(id, restTime)
    }

}