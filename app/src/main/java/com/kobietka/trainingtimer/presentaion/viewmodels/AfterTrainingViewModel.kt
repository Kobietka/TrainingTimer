package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.HistoryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class AfterTrainingViewModel
@Inject constructor(private val historyRepository: HistoryRepository){

    private val compositeDisposable = CompositeDisposable()

    val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _howManyTimes = MutableLiveData<String>()
    private val _time = MutableLiveData<String>()
    private val _greeting = MutableLiveData<String>()

    private val greetings = listOf("Nice job!", "Well done!", "Nice one!", "Good job!")

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadNumberOfTimes)
        )
    }

    fun howManyTimes(): LiveData<String> {
        return _howManyTimes
    }

    fun time(): LiveData<String> {
        return _time
    }

    fun greeting(): LiveData<String> {
        return _greeting
    }

    fun switchWorkoutId(id: Int){
        ids.onNext(id)
    }

    fun setTime(time: String){
        _time.value = time
    }

    fun randomGreeting(){
        val random = (greetings.indices).random()
        _greeting.value = greetings[random]
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



}