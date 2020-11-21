package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable


@Dao
interface ActiveGoalDao {

    @Insert
    fun insert(activeGoal: ActiveGoal): Completable

    @Query("SELECT * FROM activeGoal WHERE id = :id")
    fun getById(id: Int): Maybe<ActiveGoal>

    @Query("SELECT id FROM activeGoal")
    fun getAllIds(): Observable<List<Int>>

}