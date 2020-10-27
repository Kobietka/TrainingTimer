package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.ExerciseDao
import com.kobietka.trainingtimer.data.ExerciseEntity
import com.kobietka.trainingtimer.models.MeasurementType
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

    fun getAllIds(): Observable<List<Int>> {
        return exerciseDao.getAllIds()
    }

    fun updateName(id: Int, name: String): Completable {
        return exerciseDao.updateExerciseName(id, name)
    }

    fun updateMeasurementValue(id: Int, measurementValue: Int): Completable {
        return exerciseDao.updateExerciseMeasurementValue(id, measurementValue)
    }

    fun updateMeasurementType(id: Int, measurementType: MeasurementType): Completable {
        return exerciseDao.updateExerciseMeasurementType(id, measurementType)
    }

}