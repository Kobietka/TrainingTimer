package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable


@Dao
interface HistoryDao {

    @Insert
    fun insert(historyEntity: HistoryEntity): Completable

    @Query("SELECT id FROM history")
    fun getAllIds(): Observable<List<Int>>

    @Query("SELECT * FROM history where id = :id")
    fun getById(id: Int): Maybe<HistoryEntity>

    @Query("SELECT * FROM history where workoutId = :id")
    fun getAllEntriesByWorkoutId(id: Int): Observable<List<HistoryEntity>>

}