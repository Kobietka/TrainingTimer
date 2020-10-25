package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface WorkoutDao {

    @Query("SELECT * from workouts")
    fun getAllWorkouts(): Maybe<List<WorkoutEntity>>

    @Insert
    fun insertWorkout(workoutEntity: WorkoutEntity): Maybe<Long>

    @Query("SELECT * FROM workouts where id = :id")
    fun getById(id: Int): Maybe<WorkoutEntity>

    @Query("DELETE FROM workouts where id = :id")
    fun deleteById(id: Int?): Completable

    @Query("DELETE from workouts")
    fun deleteAllWorkouts(): Completable
    

}