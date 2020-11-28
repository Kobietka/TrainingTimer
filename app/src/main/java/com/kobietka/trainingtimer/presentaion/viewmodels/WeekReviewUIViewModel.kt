package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.data.CompletedGoal
import com.kobietka.trainingtimer.data.CompletedWorkoutEntity
import com.kobietka.trainingtimer.repositories.CompletedGoalRepository
import com.kobietka.trainingtimer.repositories.CompletedWorkoutRepository
import com.kobietka.trainingtimer.repositories.WeekRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WeekReviewUIViewModel
@Inject constructor(private val weekRepository: WeekRepository,
                    private val completedWorkoutRepository: CompletedWorkoutRepository,
                    private val completedGoalRepository: CompletedGoalRepository){

    val compositeDisposable = CompositeDisposable()

    private val _dateRange = MutableLiveData<String>()
    private val _numberOfRepetitions = MutableLiveData<String>()
    private val _time = MutableLiveData<String>()
    private val _workoutsContent = MutableLiveData<String>()
    private val _goalsContent = MutableLiveData<String>()

    fun dataRange(): LiveData<String> {
        return _dateRange
    }

    fun numberOfRepetitions(): LiveData<String> {
        return _numberOfRepetitions
    }

    fun time(): LiveData<String> {
        return _time
    }

    fun workoutsContent(): LiveData<String> {
        return _workoutsContent
    }

    fun goalsContent(): LiveData<String> {
        return _goalsContent
    }

    fun setWeekId(id: Int){
        loadWeek(id)
        loadWorkouts(id)
        loadCompletedGoals(id)
    }

    private fun loadWeek(id: Int) {
        compositeDisposable.add(
            weekRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _dateRange.value = it.dateRange
                }
        )
    }

    private fun loadWorkouts(weekId: Int){
        compositeDisposable.add(
            completedWorkoutRepository.getAllByWeekId(weekId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    calculateWorkoutValues(it)
                }
        )
    }

    private fun loadCompletedGoals(weekId: Int){
        compositeDisposable.add(
            completedGoalRepository.getByWeekId(weekId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    joinGoals(it)
                }
        )
    }

    private fun joinGoals(goals: List<CompletedGoal>){
        var goalsContent = ""
        goals.forEach {
            goalsContent = "$goalsContent ${it.name}\n"
        }
        _goalsContent.value = goalsContent
    }

    private fun calculateWorkoutValues(workouts: List<CompletedWorkoutEntity>){
        var totalRepetitionCount = 0
        var totalTime = 0
        var workoutsContent = ""
        workouts.forEach {
            totalRepetitionCount += it.workoutRepetitionCount
            totalTime += it.workoutCompletionTime
            workoutsContent = "$workoutsContent ${it.workoutName} - ${timeToString(it.workoutCompletionTime)} / ${it.workoutRepetitionCount}\n"
        }
        _numberOfRepetitions.value = totalRepetitionCount.toString()
        _time.value = timeToString(totalTime)
        _workoutsContent.value = workoutsContent
    }

    private fun timeToString(time: Int): String {
        return if(time < 3600){
            "${time/60} min"
        } else {
            "${time/3600}h ${(time - (time/3600)*3600)/60} min"
        }
    }

}