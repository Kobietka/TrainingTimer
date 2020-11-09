package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.data.WorkoutRelation
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


class ChooseExerciseUIViewModel
@Inject constructor(private val workoutRelationRepository: WorkoutRelationRepository,
                    private val exercisesRepository: ExerciseRepository){


    val compositeDisposable = CompositeDisposable()

    private val _textIfNoExercises = MutableLiveData<Boolean>()

    private val workoutIds = BehaviorSubject.create<Int>().toSerialized()
    private val exerciseIds = BehaviorSubject.create<Int>().toSerialized()

    init {
        exerciseIds.withLatestFrom(workoutIds, { exerciseId, workoutId ->
            workoutRelationRepository.insert(WorkoutRelation(null, workoutId, exerciseId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }).subscribe()

    }

    fun textIfNoExercises(): LiveData<Boolean> {
        return _textIfNoExercises
    }

    fun switchExerciseId(exerciseId: Int){
        exerciseIds.onNext(exerciseId)
    }

    fun switchWorkoutId(id: Int){
        workoutIds.onNext(id)
    }

    fun loadExercises(){
        compositeDisposable.add(
            exercisesRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _textIfNoExercises.value = it.isEmpty()
                }
        )
    }

}