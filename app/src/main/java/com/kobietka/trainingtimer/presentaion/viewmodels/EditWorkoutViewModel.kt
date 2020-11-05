package com.kobietka.trainingtimer.presentaion.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.ui.rvs.EditWorkoutAdapter
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import com.mikepenz.fastadapter.dsl.genericFastAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class EditWorkoutViewModel
@Inject constructor(private val workoutRelationRepository: WorkoutRelationRepository,
                    private val exerciseRepository: ExerciseRepository){

    private val compositeDisposable = CompositeDisposable()
    private val relationIds = BehaviorSubject.create<Int>().toSerialized()
    private val deleteClicks = BehaviorSubject.create<ClickId>().toSerialized()

    private val _exerciseName = MutableLiveData<String>()
    private val _exerciseValue = MutableLiveData<Int>()
    private val _measurementType = MutableLiveData<MeasurementType>()

    init {
        compositeDisposable.add(
            relationIds.subscribe(this::loadRelation)
        )

        compositeDisposable.add(
            deleteClicks.withLatestFrom(relationIds, { clickId, relationId ->
                workoutRelationRepository.deleteById(relationId)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
            }).subscribe()
        )

    }

    fun exerciseValue(): LiveData<Int> {
        return _exerciseValue
    }

    fun exerciseName(): LiveData<String> {
        return _exerciseName
    }

    fun measurementType(): LiveData<MeasurementType> {
        return _measurementType
    }

    fun switchId(id: Int){
        relationIds.onNext(id)
    }

    fun onDeleteClick(){
        deleteClicks.onNext(ClickId.Delete)
        compositeDisposable.clear()
    }

    private fun loadRelation(id: Int){
        compositeDisposable.add(
            workoutRelationRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loadExercise(it)
                }
        )
    }

    private fun loadExercise(id: Int){
        compositeDisposable.add(
            exerciseRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _exerciseName.value = it.name
                    _exerciseValue.value = it.measurementValue
                    _measurementType.value = it.measurementType
                }
        )
    }

}