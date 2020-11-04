package com.kobietka.trainingtimer.presentaion.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.data.WorkoutRelation
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.models.WorkoutAddExerciseEvent
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ChooseExerciseViewModel
@Inject constructor(private val workoutRelationRepository: WorkoutRelationRepository,
                    private val exerciseRepository: ExerciseRepository,
                    private val workoutIdSender: Subject<Int>,
                    private val launchEvents: Subject<EventType>){

    private val compositeDisposable = CompositeDisposable()
    private val addClicks = BehaviorSubject.create<ClickId>().toSerialized()
    private val workoutIds = BehaviorSubject.create<Int>().toSerialized()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _measurementValue = MutableLiveData<Int>()
    private val _measurementType = MutableLiveData<MeasurementType>()

    init {
        compositeDisposable.add(
            workoutIdSender.subscribe(this::switchWorkoutId)
        )

        compositeDisposable.add(
            ids.subscribe(this::loadExercise)
        )

        addClicks.withLatestFrom(ids, workoutIds, { clickId, exerciseId, workoutId ->
            workoutRelationRepository.insert(WorkoutRelation(null, workoutId, exerciseId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    launchEvents.onNext(EventType(clickId, workoutId))
                }

        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
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

    private fun switchWorkoutId(id: Int){
        workoutIds.onNext(id)
    }

    fun onAddClick(){
        addClicks.onNext(ClickId.EditWorkoutFromChoosing)
    }

    fun switchId(id: Int){
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