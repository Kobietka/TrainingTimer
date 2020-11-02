package com.kobietka.trainingtimer.presentaion.viewmodels

import com.kobietka.trainingtimer.data.WorkoutEntity
import com.kobietka.trainingtimer.presentaion.ui.rvs.AddWorkoutAdapter
import com.kobietka.trainingtimer.presentaion.ui.rvs.EditWorkoutAdapter
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class AddWorkoutUIViewModel
@Inject constructor(private val workoutRepository: WorkoutRepository){

    val compositeDisposable = CompositeDisposable()

    lateinit var addAdapter: AddWorkoutAdapter
    var workout = -1

    fun setId(id: Int){
        workout = id
    }

    fun getWorkoutId(): Int {
        return workout
    }

    fun setAdapter(adapter: AddWorkoutAdapter){
        addAdapter = adapter
    }

    fun saveWorkout(name: String, restTime: Int) {

        val calendar = Calendar.getInstance()
        val creationDate = calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                calendar.get(Calendar.MONTH).toString() + "/" +
                calendar.get(Calendar.YEAR).toString()

        compositeDisposable.add(
            workoutRepository.insertWorkout(
                WorkoutEntity(
                    null,
                    name,
                    restTime,
                    creationDate,
                    0,
                    0
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    setId(it.toInt())
                }
        )
    }

}