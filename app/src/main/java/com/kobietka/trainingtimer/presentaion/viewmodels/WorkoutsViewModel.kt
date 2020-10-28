package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class WorkoutsViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository,
                    private val eventSubject: Subject<EventType>){

    private val compositeDisposable = CompositeDisposable()
    private val deleteClicks = BehaviorSubject.create<Int>().toSerialized()
    private val editClicks = BehaviorSubject.create<Int>().toSerialized()
    private val ids = BehaviorSubject.create<Int>().toSerialized()

    private val _name = MutableLiveData<String>()
    private val _numberOfExercises = MutableLiveData<Int>()

    init {
        editClicks.withLatestFrom(ids, { clickId, workoutId ->
            eventSubject.onNext(EventType(clickId, workoutId))
        }).subscribe()

        deleteClicks.withLatestFrom(ids, { clickId, workoutId ->
            workoutRepository.deleteById(workoutId)
        }).flatMapCompletable { it }.subscribe()
    }

    fun name(): LiveData<String> = _name
    fun numberOfExercises(): LiveData<Int> = _numberOfExercises

    fun onEditClick(){
        editClicks.onNext(2)
    }

    fun onDeleteClick(){
        deleteClicks.onNext(0)
    }

    fun switchId(id: Int){
        loadWorkout(id)
        ids.onNext(id)
    }

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _name.value = it.name
                    _numberOfExercises.value = it.exercises.size
                }
        )
    }

}