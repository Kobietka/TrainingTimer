package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.ExerciseDao
import com.kobietka.trainingtimer.data.ExerciseEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject


class ExerciseRepository
@Inject constructor(private val exerciseDao: ExerciseDao){

    fun getAllExercises(): Observable<List<ExerciseEntity>> {
        return exerciseDao.getAllExercises()
    }

    fun insertExercise(exerciseEntity: ExerciseEntity): Completable {
        return exerciseDao.insertExercise(exerciseEntity)
    }

    fun deleteAllExercises(): Completable {
        return exerciseDao.deleteAllExercises()
    }

    fun getById(id: Int): Observable<ExerciseEntity> {
        return exerciseDao.getById(id)
    }

    fun deleteById(id: Int): Completable {
        return exerciseDao.deleteById(id)
    }

    fun getExerciseCountByWorkoutId(id: Int): Observable<Int> {
        return exerciseDao.getExerciseCountByWorkoutId(id)
    }

}