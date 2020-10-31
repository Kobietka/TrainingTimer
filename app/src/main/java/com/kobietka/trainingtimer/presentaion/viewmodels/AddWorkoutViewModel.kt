package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.data.WorkoutEntity
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject


class AddWorkoutViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val exerciseRepository: ExerciseRepository){

    private val compositeDisposable = CompositeDisposable()
    private val deleteClicks = BehaviorSubject.create<Int>().toSerialized()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _measurementValue = MutableLiveData<Int>()
    private val _measurementType = MutableLiveData<MeasurementType>()

    private val exercises = mutableListOf<Int>()

    init {
        deleteClicks.withLatestFrom(ids, { clickId, exerciseId ->
            exercises.remove(exerciseId)
        }).subscribe()
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

    fun addExercise(id: Int){
        exercises.add(id)
    }

    fun onDeleteClick(){
        deleteClicks.onNext(0)
    }

    fun saveWorkout(name: String, restTime: Int){

        val calendar = Calendar.getInstance()
        val creationDate = calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                calendar.get(Calendar.MONTH).toString() + "/" +
                calendar.get(Calendar.YEAR).toString()

        workoutRepository.insertWorkout(WorkoutEntity(
            null,
            name,
            restTime,
            exercises,
            creationDate,
            0,
            0
        )).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    fun switchId(id: Int){
        if(exercises.contains(id)) {
            loadExercise(id)
            ids.onNext(id)
        }
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
                    exercises.add(id)
                }
        )
    }

}