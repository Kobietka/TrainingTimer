package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class EditWorkoutViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val exerciseRepository: ExerciseRepository,
                    private val eventSubject: Subject<EventType>){

    private val compositeDisposable = CompositeDisposable()
    private val ids = BehaviorSubject.create<Int>().toSerialized()
    private val addClicks = BehaviorSubject.create<ClickId>().toSerialized()
    private var workoutId = 0

    private val _name = MutableLiveData<String>()
    private val _restTime = MutableLiveData<Int>()

    private val _exerciseName = MutableLiveData<String>()
    private val _exerciseValue = MutableLiveData<Int>()
    private val _measurementType = MutableLiveData<MeasurementType>()

    fun name(): LiveData<String> {
        return _name
    }

    fun restTime(): LiveData<Int> {
        return _restTime
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

    fun setId(id: Int){
        workoutId = id
    }

    fun switchId(id: Int){
        ids.onNext(id)
        loadExercise(id)
    }

    fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                    _restTime.value = it.restTime
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