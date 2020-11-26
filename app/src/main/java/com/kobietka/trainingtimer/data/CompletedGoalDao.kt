package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface CompletedGoalDao {

    @Insert
    fun insert(completedGoal: CompletedGoal): Completable

    @Query("SELECT * FROM completedGoal WHERE id = :id")
    fun getById(id: Int): Maybe<CompletedGoal>

    @Query("SELECT id FROM completedGoal")
    fun getAllIds(): Observable<List<Int>>

    @Query("DELETE FROM completedGoal")
    fun deleteAll(): Completable

}