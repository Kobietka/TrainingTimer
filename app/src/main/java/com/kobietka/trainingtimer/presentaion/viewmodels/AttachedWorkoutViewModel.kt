package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class AttachedWorkoutViewModel
@Inject constructor(private val workoutsRepository: WorkoutRepository){

    val compositeDisposable = CompositeDisposable()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadWorkout)
        )
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun switchId(id: Int){
        ids.onNext(id)
    }

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutsRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                }
        )
    }

}