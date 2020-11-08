package com.kobietka.trainingtimer.presentaion.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.presentaion.ui.rvs.EditWorkoutAdapter
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class EditWorkoutUIViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val workoutRelationRepository: WorkoutRelationRepository){

    val compositeDisposable = CompositeDisposable()
    val ids = BehaviorSubject.create<Int>().toSerialized()
    private val names = BehaviorSubject.create<String>().toSerialized()
    private val restTimes = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _restTime = MutableLiveData<Int>()

    init {
        compositeDisposable.add(
            ids.subscribe(this::loadWorkout)
        )

        restTimes.withLatestFrom(ids, names, { restTime, id, name ->
            saveWorkout(id, name, restTime)
        }).subscribe()
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun restTime(): LiveData<Int> {
        return _restTime
    }

    fun switchId(id: Int){
        ids.onNext(id)
    }

    fun onSaveClick(name: String, restTime: Int){
        names.onNext(name)
        restTimes.onNext(restTime)
    }

    fun deleteRelation(id: Int){
        compositeDisposable.add(
            workoutRelationRepository.deleteById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun saveWorkout(workoutId: Int, name: String, restTime: Int){
        compositeDisposable.add(
            workoutRepository.updateName(workoutId, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        compositeDisposable.add(
            workoutRepository.updateRestTime(workoutId, restTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun loadWorkout(id: Int){
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

}