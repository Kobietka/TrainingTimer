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

    @Query("SELECT id from workouts")
    fun getAllIds(): Observable<List<Int>>

    @Query("UPDATE workouts SET name =:name where id= :id")
    fun updateName(id: Int, name: String): Completable

    @Query("UPDATE workouts SET restTime =:restTime where id= :id")
    fun updateRestTime(id: Int, restTime: Int): Completable

}