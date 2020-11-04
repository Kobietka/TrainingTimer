package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.data.ExerciseEntity
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditExerciseViewModel
@Inject constructor(private val exerciseRepository: ExerciseRepository){

    val compositeDisposable = CompositeDisposable()
    private var exerciseId = 0

    private val _name = MutableLiveData<String>()
    private val _measurementValue = MutableLiveData<Int>()
    private val _measurementType = MutableLiveData<MeasurementType>()

    fun measurementValue(): LiveData<Int> {
        return _measurementValue
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun measurementType(): LiveData<MeasurementType> {
        return _measurementType
    }

    fun loadDataById(id: Int){
        compositeDisposable.add(
            exerciseRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                    _measurementType.value = it.measurementType
                    _measurementValue.value = it.measurementValue
                }
        )
    }

    fun setId(id: Int){
        exerciseId = id
    }

    fun saveExercise(name: String, measurementType: String, measurementValue: Int){
        compositeDisposable.add(
            exerciseRepository.updateName(exerciseId, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        compositeDisposable.add(
            exerciseRepository.updateMeasurementValue(exerciseId, measurementValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        val measurement = when(measurementType){
            "Time" -> MeasurementType.Time
            "Repetition" -> MeasurementType.Repetition
            else -> null
        }

        compositeDisposable.add(
            exerciseRepository.updateMeasurementType(exerciseId, measurement!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

}