package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.WeekDao
import com.kobietka.trainingtimer.data.WeekEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject


class WeekRepository
@Inject constructor(private val weekDao: WeekDao){

    fun insert(weekEntity: WeekEntity): Completable {
        return weekDao.insert(weekEntity)
    }

    fun getById(id: Int): Maybe<WeekEntity> {
        return weekDao.getById(id)
    }

    fun getAllIds(): Observable<List<Int>> {
        return weekDao.getAllIds()
    }

    fun deleteAll(): Completable {
        return weekDao.deleteAll()
    }

    fun getActiveWeekId(isActive: Boolean): Maybe<Int> {
        return weekDao.getActiveWeekId(isActive)
    }

}