package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.HistoryDao
import com.kobietka.trainingtimer.data.HistoryEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject


class HistoryRepository
@Inject constructor(private val historyDao: HistoryDao){

    fun insert(historyEntity: HistoryEntity): Completable {
        return historyDao.insert(historyEntity)
    }

    fun getAllIds(): Observable<List<Int>> {
        return historyDao.getAllIds()
    }

    fun getById(id: Int): Maybe<HistoryEntity> {
        return historyDao.getById(id)
    }

    fun getAllEntriesByWorkoutId(id: Int): Observable<List<HistoryEntity>> {
        return historyDao.getAllEntriesByWorkoutId(id)
    }

}