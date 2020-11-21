package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.ActiveGoal
import com.kobietka.trainingtimer.data.ActiveGoalDao
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject


class ActiveGoalRepository
@Inject constructor(private val activeGoalDao: ActiveGoalDao){

    fun insert(activeGoal: ActiveGoal): Completable {
        return activeGoalDao.insert(activeGoal)
    }

    fun getById(id: Int): Maybe<ActiveGoal> {
        return activeGoalDao.getById(id)
    }

    fun getAllIds(): Observable<List<Int>> {
        return activeGoalDao.getAllIds()
    }

}