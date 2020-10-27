package com.kobietka.trainingtimer.presentaion.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kobietka.trainingtimer.data.ExerciseEntity
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class ExerciseViewModel
@Inject constructor(private val exerciseRepository: ExerciseRepository) {

    private val compositeDisposable = CompositeDisposable()

    private val _name = MutableLiveData<String>()
    private val _measurementValue = MutableLiveData<Int>()

    fun measurementValue(): LiveData<Int> {
        return _measurementValue
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun switchId(id: Int){
        loadExercise(id)
    }

    private fun loadExercise(id: Int) {
        compositeDisposable.add(
            exerciseRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.name
                }.subscribe {
                    _name.value = it
                }
        )

        compositeDisposable.add(
            exerciseRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    it.measurementValue
                }.subscribe {
                    _measurementValue.value = it
                }
        )
    }

}