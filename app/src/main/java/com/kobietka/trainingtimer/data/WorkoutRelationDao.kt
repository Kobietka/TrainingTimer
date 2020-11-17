package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable


@Dao
interface WorkoutRelationDao {

    @Insert
    fun insert(workoutRelation: WorkoutRelation): Maybe<Long>

    @Query("DELETE FROM workoutRelation where workoutId = :id")
    fun deleteByWorkoutId(id: Int): Completable

    @Query("SELECT exerciseId FROM workoutRelation where workoutId = :id")
    fun getExercisesByWorkoutId(id: Int): Observable<List<Int>>

    @Query("SELECT id FROM workoutRelation where workoutId = :id")
    fun getRelationIdsByWorkoutId(id: Int): Observable<List<Int>>

    @Query("SELECT exerciseId FROM workoutRelation where id = :id")
    fun getById(id: Int): Maybe<Int>

    @Query("DELETE FROM workoutRelation where exerciseId = :id")
    fun deleteByExerciseId(id: Int): Completable

    @Query("DELETE FROM workoutRelation where id = :id")
    fun deleteById(id: Int): Completable

    @Query("SELECT workoutId FROM workoutRelation where exerciseId = :id")
    fun getByExerciseId(id: Int): Observable<List<Int>>

}