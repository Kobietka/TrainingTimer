package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.WorkoutRelation
import com.kobietka.trainingtimer.data.WorkoutRelationDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class WorkoutRelationRepository
@Inject constructor(private val workoutRelationDao: WorkoutRelationDao){

    fun insert(workoutRelation: WorkoutRelation): Maybe<Long> {
        return workoutRelationDao.insert(workoutRelation)
    }

    fun deleteByWorkoutId(id: Int): Completable {
        return workoutRelationDao.deleteByWorkoutId(id)
    }

    fun getExercisesByWorkoutId(id: Int): Observable<List<Int>> {
        return workoutRelationDao.getExercisesByWorkoutId(id)
    }

    fun deleteByExerciseId(id: Int): Completable {
        return workoutRelationDao.deleteByExerciseId(id)
    }

    fun deleteById(id: Int): Completable {
        return workoutRelationDao.deleteById(id)
    }

    fun getById(id: Int): Maybe<Int> {
        return workoutRelationDao.getById(id)
    }

    fun getRelationIdsByWorkoutId(workoutId: Int): Observable<List<Int>> {
        return workoutRelationDao.getRelationIdsByWorkoutId(workoutId)
    }

}