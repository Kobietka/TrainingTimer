package com.kobietka.trainingtimer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobietka.trainingtimer.models.MeasurementType
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable


@Dao
interface ActiveGoalDao {

    @Insert
    fun insert(activeGoal: ActiveGoal): Completable

    @Query("SELECT * FROM activeGoal WHERE id = :id")
    fun getById(id: Int): Maybe<ActiveGoal>

    @Query("SELECT * FROM activeGoal")
    fun getAllGoals(): Maybe<List<ActiveGoal>>

    @Query("SELECT id FROM activeGoal")
    fun getAllIds(): Observable<List<Int>>

    @Query("DELETE FROM activeGoal WHERE id = :id")
    fun deleteById(id: Int): Completable

    @Query("UPDATE activeGoal SET name = :name WHERE id = :goalId")
    fun updateName(name: String, goalId: Int): Completable

    @Query("UPDATE activeGoal SET goal = :goal WHERE id = :goalId")
    fun updateGoal(goal: Int, goalId: Int): Completable

    @Query("UPDATE activeGoal SET currentProgress = :current WHERE id = :goalId")
    fun updateProgress(current: Int, goalId: Int): Completable

    @Query("UPDATE activeGoal SET type = :type WHERE id = :goalId")
    fun updateType(type: MeasurementType, goalId: Int): Completable

    @Query("UPDATE activeGoal SET workoutId = :workoutId WHERE id = :goalId")
    fun updateWorkoutId(workoutId: Int?, goalId: Int): Completable
}