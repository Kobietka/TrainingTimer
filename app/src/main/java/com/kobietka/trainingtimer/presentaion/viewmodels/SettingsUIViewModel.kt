package com.kobietka.trainingtimer.presentaion.viewmodels

import com.kobietka.trainingtimer.repositories.CompletedGoalRepository
import com.kobietka.trainingtimer.repositories.HistoryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SettingsUIViewModel
@Inject constructor(private val historyRepository: HistoryRepository,
                    private val completedGoalRepository: CompletedGoalRepository){

    val compositeDisposable = CompositeDisposable()

    fun onDeleteHistory(){
        compositeDisposable.add(
            historyRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun onDeleteCompletedGoals(){
        compositeDisposable.add(
            completedGoalRepository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}