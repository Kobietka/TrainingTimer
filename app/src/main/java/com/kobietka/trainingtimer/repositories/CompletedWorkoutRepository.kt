package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.CompletedWorkoutDao
import com.kobietka.trainingtimer.data.CompletedWorkoutEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject


class CompletedWorkoutRepository
@Inject constructor(private val completedWorkoutDao: CompletedWorkoutDao){

    fun insert(completedWorkoutEntity: CompletedWorkoutEntity): Completable {
        return completedWorkoutDao.insert(completedWorkoutEntity)
    }

    fun deleteAll(): Completable {
        return completedWorkoutDao.deleteAll()
    }

    fun getById(id: Int): Maybe<CompletedWorkoutEntity> {
        return completedWorkoutDao.getById(id)
    }

    fun getAllByWeekId(weekId: Int?): Maybe<List<CompletedWorkoutEntity>> {
        return completedWorkoutDao.getAllByWeekId(weekId)
    }

    fun getAllIds(): Observable<List<Int>> {
        return completedWorkoutDao.getAllIds()
    }
    
}