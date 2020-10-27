package com.kobietka.trainingtimer.presentaion.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kobietka.trainingtimer.data.ExerciseEntity
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class ExerciseViewModel
@Inject constructor(private val exerciseRepository: ExerciseRepository,
                    private val eventSubject: Subject<EventType>) {

    private val compositeDisposable = CompositeDisposable()
    private val deleteClicks = BehaviorSubject.create<Int>().toSerialized()
    private val editClicks = BehaviorSubject.create<Int>().toSerialized()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _measurementValue = MutableLiveData<Int>()
    private val _measurementType = MutableLiveData<MeasurementType>()


    init {
        editClicks.withLatestFrom(ids, { clickId, exerciseId ->
            eventSubject.onNext(EventType(clickId, exerciseId))
        }).subscribe()

        deleteClicks.withLatestFrom(ids, BiFunction<Int, Int, Completable> { clickId, exerciseId ->
            exerciseRepository.deleteById(exerciseId)
        }).flatMapCompletable { it }.subscribe()
    }

    fun measurementValue(): LiveData<Int> {
        return _measurementValue
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun measurementType(): LiveData<MeasurementType> {
        return _measurementType
    }

    fun onEditClick(){
        editClicks.onNext(1)
    }

    fun onDeleteClick(){
        deleteClicks.onNext(0)
    }

    fun switchId(id: Int){
        loadExercise(id)
        ids.onNext(id)
    }

    private fun loadExercise(id: Int) {
        compositeDisposable.add(
            exerciseRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                    _measurementValue.value = it.measurementValue
                    _measurementType.value = it.measurementType
                }
        )
    }

}