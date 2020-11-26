package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface WeekDao {

    @Insert
    fun insert(weekEntity: WeekEntity): Completable

    @Query("SELECT * FROM week WHERE id = :id")
    fun getById(id: Int): Maybe<WeekEntity>

    @Query("SELECT id FROM week")
    fun getAllIds(): Observable<List<Int>>

    @Query("DELETE FROM week")
    fun deleteAll(): Completable

}