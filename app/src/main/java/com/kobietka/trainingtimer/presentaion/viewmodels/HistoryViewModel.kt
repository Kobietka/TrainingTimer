package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.HistoryRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class HistoryViewModel
@Inject constructor(private val historyRepository: HistoryRepository,
                    private val workoutRepository: WorkoutRepository){


    private val compositeDisposable = CompositeDisposable()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _completeDate = MutableLiveData<String>()

    init {
        compositeDisposable.add(
            ids.subscribe {
                loadHistory(it)
                loadWorkout(it)
            }
        )
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun completeDate(): LiveData<String> {
        return _completeDate
    }

    fun switchId(id: Int){
        ids.onNext(id)
    }

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                }
        )
    }

    private fun loadHistory(id: Int){
        compositeDisposable.add(
            historyRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _completeDate.value = it.completeDate
                }
        )
    }

}