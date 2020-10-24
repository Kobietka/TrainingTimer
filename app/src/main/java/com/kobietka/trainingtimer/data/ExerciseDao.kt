package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ExerciseDao {

    @Query("SELECT * from exercises")
    fun getAllExercises(): Observable<List<ExerciseEntity>>

    @Insert
    fun insertExercise(exerciseEntity: ExerciseEntity): Completable

    @Query("Delete from exercises")
    fun deleteAllExercises(): Completable

    @Query("SELECT * FROM exercises where id = :id")
    fun getById(id: Int?): Observable<ExerciseEntity>

    @Query("DELETE FROM exercises where id = :id")
    fun deleteById(id: Int?): Completable

    @Query("SELECT COUNT(*) from exercises WHERE workoutId = :id")
    fun getExerciseCountByWorkoutId(id: Int?): Observable<Int>

}