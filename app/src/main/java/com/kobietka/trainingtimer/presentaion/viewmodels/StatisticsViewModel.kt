package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.CompletedWorkoutRepository
import com.kobietka.trainingtimer.repositories.WeekRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class StatisticsViewModel
@Inject constructor(private val weekRepository: WeekRepository,
                    private val completedWorkoutRepository: CompletedWorkoutRepository){

    private val compositeDisposable = CompositeDisposable()

    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _dateRange = MutableLiveData<String>()
    private val _workoutCount = MutableLiveData<String>()

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadWeek)
        )
    }

    fun dataRange(): LiveData<String> {
        return _dateRange
    }

    fun workoutCount(): LiveData<String> {
        return _workoutCount
    }

    fun switchId(id: Int){
        ids.onNext(id)
    }

    private fun loadWeek(id: Int){
        compositeDisposable.add(
            weekRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _dateRange.value = it.dateRange
                    getWorkoutCountByWeekId(it.id!!)
                }
        )
    }

    private fun getWorkoutCountByWeekId(id: Int){
        compositeDisposable.add(
            completedWorkoutRepository.getAllByWeekId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _workoutCount.value = it.size.toString()
                }
        )
    }

}