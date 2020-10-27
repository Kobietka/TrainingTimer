package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobietka.trainingtimer.models.MeasurementType
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ExerciseDao {

    @Query("SELECT * from exercises")
    fun getAllExercises(): Maybe<List<ExerciseEntity>>

    @Insert
    fun insertExercise(exerciseEntity: ExerciseEntity): Maybe<Long>

    @Query("Delete from exercises")
    fun deleteAllExercises(): Completable

    @Query("SELECT * FROM exercises where id = :id")
    fun getById(id: Int?): Maybe<ExerciseEntity>

    @Query("DELETE FROM exercises where id = :id")
    fun deleteById(id: Int?): Completable

    @Query("SELECT id from exercises")
    fun getAllIds(): Observable<List<Int>>

    @Query("UPDATE exercises SET name =:name where id= :id")
    fun updateExerciseName(id: Int, name: String): Completable

    @Query("UPDATE exercises SET measurementType =:measurementType where id= :id")
    fun updateExerciseMeasurementType(id: Int, measurementType: MeasurementType): Completable

    @Query("UPDATE exercises SET measurementValue =:measurementValue where id= :id")
    fun updateExerciseMeasurementValue(id: Int, measurementValue: Int): Completable


}