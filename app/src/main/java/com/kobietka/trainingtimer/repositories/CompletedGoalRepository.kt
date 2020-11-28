package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.CompletedGoal
import com.kobietka.trainingtimer.data.CompletedGoalDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject


class CompletedGoalRepository
@Inject constructor(private val completedGoalDao: CompletedGoalDao){

    fun insert(completedGoal: CompletedGoal): Completable {
        return completedGoalDao.insert(completedGoal)
    }

    fun getById(id: Int): Maybe<CompletedGoal> {
        return completedGoalDao.getById(id)
    }

    fun getAllIds(): Observable<List<Int>> {
        return completedGoalDao.getAllIds()
    }

    fun deleteAll(): Completable {
        return completedGoalDao.deleteAll()
    }

    fun getByWeekId(weekId: Int): Maybe<List<CompletedGoal>> {
        return completedGoalDao.getByWeekId(weekId)
    }

}