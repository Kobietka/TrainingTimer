package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExercisesUIViewModel
@Inject constructor(private val exerciseRepository: ExerciseRepository,
                    private val workoutRelationRepository: WorkoutRelationRepository){

    val compositeDisposable = CompositeDisposable()
    private val _textIfNoExercises = MutableLiveData<Boolean>()

    lateinit var isInWorkoutsAction: (exist: Boolean, id: Int) -> Unit

    fun textIfNoExercises(): LiveData<Boolean> {
        return _textIfNoExercises
    }

    fun deleteExercise(id: Int){
        exerciseRepository.deleteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        workoutRelationRepository.deleteByExerciseId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun checkIfExerciseIsInWorkouts(id: Int){
        compositeDisposable.add(
            workoutRelationRepository.getByExerciseId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isInWorkoutsAction.invoke(it.isNotEmpty(), id)
                }
        )
    }

    fun loadExercises(){
        compositeDisposable.add(
            exerciseRepository.getAllIds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _textIfNoExercises.value = it.isEmpty()
                }
        )
    }

    fun setIsInWorkout(function: (exist: Boolean, id: Int) -> Unit){
        isInWorkoutsAction = function
    }

}