package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable


@Dao
interface CompletedWorkoutDao {

    @Insert
    fun insert(completedWorkoutEntity: CompletedWorkoutEntity): Completable

    @Query("DELETE FROM completedWorkout")
    fun deleteAll(): Completable

    @Query("SELECT * FROM completedWorkout WHERE id = :id")
    fun getById(id: Int): Maybe<CompletedWorkoutEntity>

    @Query("SELECT * FROM completedWorkout WHERE weekId = :weekId")
    fun getAllByWeekId(weekId: Int?): Maybe<List<CompletedWorkoutEntity>>

    @Query("SELECT id FROM completedWorkout")
    fun getAllIds(): Observable<List<Int>>

}