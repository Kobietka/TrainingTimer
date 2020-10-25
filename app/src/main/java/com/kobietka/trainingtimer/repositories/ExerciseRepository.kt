package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.ExerciseDao
import com.kobietka.trainingtimer.data.ExerciseEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class ExerciseRepository
@Inject constructor(private val exerciseDao: ExerciseDao){

    fun getAllExercises(): Maybe<List<ExerciseEntity>> {
        return exerciseDao.getAllExercises()
    }

    fun insertExercise(exerciseEntity: ExerciseEntity): Maybe<Long> {
        return exerciseDao.insertExercise(exerciseEntity)
    }

    fun deleteAllExercises(): Completable {
        return exerciseDao.deleteAllExercises()
    }

    fun getById(id: Int): Maybe<ExerciseEntity> {
        return exerciseDao.getById(id)
    }

    fun deleteById(id: Int): Completable {
        return exerciseDao.deleteById(id)
    }

    fun getExerciseCountByWorkoutId(id: Int): Single<Int> {
        return exerciseDao.getExerciseCountByWorkoutId(id)
    }

}