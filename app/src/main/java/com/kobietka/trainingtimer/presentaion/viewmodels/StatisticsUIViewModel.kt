package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.WeekRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class StatisticsUIViewModel
@Inject constructor(private val weekRepository: WeekRepository){

    val compositeDisposable = CompositeDisposable()

    private val _noWeeks = MutableLiveData<Boolean>()

    fun noWeeks(): LiveData<Boolean> {
        return _noWeeks
    }

    fun checkWeeks(){
        compositeDisposable.add(
            weekRepository.getIdsOfNotActiveWeeks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _noWeeks.value = it.isNotEmpty()
                }
        )
    }

}