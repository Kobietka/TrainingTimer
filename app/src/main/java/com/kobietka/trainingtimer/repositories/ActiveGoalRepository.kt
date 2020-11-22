package com.kobietka.trainingtimer.repositories

import com.kobietka.trainingtimer.data.ActiveGoal
import com.kobietka.trainingtimer.data.ActiveGoalDao
import com.kobietka.trainingtimer.models.MeasurementType
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

    fun getAllGoals(): Maybe<List<ActiveGoal>> {
        return activeGoalDao.getAllGoals()
    }

    fun deleteById(id: Int): Completable {
        return activeGoalDao.deleteById(id)
    }

    fun updateProgress(current: Int, goalId: Int): Completable {
        return activeGoalDao.updateProgress(current, goalId)
    }

    fun updateName(name: String, id: Int): Completable {
        return activeGoalDao.updateName(name, id)
    }

    fun updateGoal(goal: Int, id: Int): Completable {
        return activeGoalDao.updateGoal(goal, id)
    }

    fun updateType(type: MeasurementType, id: Int): Completable {
        return activeGoalDao.updateType(type, id)
    }

    fun updateWorkoutId(workoutId: Int?, goalId: Int): Completable {
        return activeGoalDao.updateWorkoutId(workoutId, goalId)
    }

}