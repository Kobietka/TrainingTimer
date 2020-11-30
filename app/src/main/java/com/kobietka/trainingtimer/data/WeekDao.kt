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

    @Query("SELECT id FROM week")
    fun getAllIdsMaybe(): Maybe<List<Int>>

    @Query("DELETE FROM week")
    fun deleteAll(): Completable

    @Query("SELECT id FROM week WHERE isActive = :isActive")
    fun getActiveWeekId(isActive: Boolean): Maybe<Int>

    @Query("UPDATE week SET isActive = :isActive WHERE id = :id")
    fun updateActiveStatus(isActive: Boolean, id: Int): Completable

    @Query("SELECT id FROM week WHERE isActive = :active")
    fun getIdsOfNotActiveWeeks(active: Boolean): Observable<List<Int>>

}