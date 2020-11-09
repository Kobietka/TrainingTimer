package com.kobietka.trainingtimer.presentaion.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ExercisesUIViewModel
@Inject constructor(private val exerciseRepository: ExerciseRepository){

    val compositeDisposable = CompositeDisposable()
    private val _textIfNoExercises = MutableLiveData<Boolean>()

    fun textIfNoExercises(): LiveData<Boolean> {
        return _textIfNoExercises
    }

    fun deleteExercise(id: Int){
        exerciseRepository.deleteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
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

}