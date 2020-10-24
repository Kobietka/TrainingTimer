package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Query("SELECT COUNT(*) from exercises WHERE workoutId = :id")
    fun getExerciseCountByWorkoutId(id: Int?): Single<Int>

}