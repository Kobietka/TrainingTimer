package com.kobietka.trainingtimer.presentaion.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.data.ActiveGoal
import com.kobietka.trainingtimer.data.CompletedGoal
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.common.TimeToString
import com.kobietka.trainingtimer.repositories.ActiveGoalRepository
import com.kobietka.trainingtimer.repositories.CompletedGoalRepository
import com.kobietka.trainingtimer.repositories.HistoryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject


class AfterTrainingViewModel
@Inject constructor(private val historyRepository: HistoryRepository,
                    private val activeGoalRepository: ActiveGoalRepository,
                    private val completedGoalRepository: CompletedGoalRepository){

    private val compositeDisposable = CompositeDisposable()
    private val timeConverter = TimeToString()

    val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _howManyTimes = MutableLiveData<String>()
    private val _time = MutableLiveData<String>()
    private val _greeting = MutableLiveData<String>()
    private val _repetitions = MutableLiveData<String>()

    private val greetings = listOf("Nice job!", "Well done!", "Nice one!", "Good job!")
    private var goals = listOf<ActiveGoal>()
    var currentWorkoutId = 0
    var repetitionCount = 0
    var time = 0
    var isAddingProgress = true

    init {
        compositeDisposable.add(
            ids.subscribe {
                loadNumberOfTimes(it)
            }
        )
    }


    fun howManyTimes(): LiveData<String> {
        return _howManyTimes
    }

    fun repetitions(): LiveData<String> {
        return _repetitions
    }

    fun time(): LiveData<String> {
        return _time
    }

    fun greeting(): LiveData<String> {
        return _greeting
    }

    fun switchWorkoutId(id: Int){
        ids.onNext(id)
        currentWorkoutId = id
        loadGoals()
    }

    fun setTime(time: String){
        _time.value = timeConverter.convert(time.toInt())
        this.time = time.toInt()
    }

    fun setRepetitions(repetitions: String){
        _repetitions.value = "Repetitions: $repetitions"
        this.repetitionCount = repetitions.toInt()
    }

    fun randomGreeting(){
        val random = (greetings.indices).random()
        _greeting.value = greetings[random]
    }

    private fun addProgressToGoals(){
        Log.e("AddProgress", "called")
        goals.forEach {
            if(it.isAttached){
                if(it.workoutId == currentWorkoutId){
                    when(it.type){
                        MeasurementType.Repetition -> updateRepetition(repetitionCount + it.currentProgress, it.id!!)
                        MeasurementType.Time -> updateTime(time/60 + it.currentProgress, it.id!!)
                    }
                }
            } else {
                when(it.type){
                    MeasurementType.Repetition -> updateRepetition(repetitionCount + it.currentProgress, it.id!!)
                    MeasurementType.Time -> updateTime(time/60 + it.currentProgress, it.id!!)
                }
            }
        }
        isAddingProgress = false
    }

    private fun checkIfGoalsCompleted(){
        Log.e("Check", "called")
        if(goals.isEmpty()) return
        goals.forEach {
            Log.e("TIMES", "OUT")
            if(it.currentProgress >= it.goal){
                Log.e("TIMES", "IN")
                completedGoalRepository.insert(
                    CompletedGoal(null, it.name, it.goal, it.type, it.workoutId, it.creationDate, getCurrentDate())
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                activeGoalRepository.deleteById(it.id!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }
    }


    private fun loadGoals(){
        Log.e("Load", "called")
        compositeDisposable.add(
            activeGoalRepository.getAllGoals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    goals = it
                    if(isAddingProgress) addProgressToGoals()
                    else checkIfGoalsCompleted()
                }
        )
    }

    private fun updateRepetition(repetition: Int, goalId: Int){
        compositeDisposable.add(
            activeGoalRepository.updateProgress(repetition, goalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadGoals)
        )
    }

    private fun updateTime(time: Int, goalId: Int){
        compositeDisposable.add(
            activeGoalRepository.updateProgress(time, goalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadGoals)
        )
    }

    private fun loadNumberOfTimes(id: Int){
        compositeDisposable.add(
            historyRepository.getAllEntriesByWorkoutId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _howManyTimes.value = it.size.toString()
                }
        )
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                calendar.get(Calendar.MONTH).toString() + "/" +
                calendar.get(Calendar.YEAR).toString()
    }

}