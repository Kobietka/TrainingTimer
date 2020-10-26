package com.kobietka.trainingtimer.presentaion.viewmodels

import com.kobietka.trainingtimer.data.ExerciseEntity
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import java.util.*
import javax.inject.Inject


class AddExerciseViewModel
@Inject constructor(private val exerciseRepository: ExerciseRepository){

    fun createExercise(exerciseName: String, measurement: String, measurementValue: Int){

        val measurementType: MeasurementType? = when (measurement){
            "Time" -> MeasurementType.Time
            "Repetition" -> MeasurementType.Repetition
            else -> null
        }

        val calendar = Calendar.getInstance()
        val creationDate = calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                calendar.get(Calendar.MONTH).toString() + "/" +
                calendar.get(Calendar.YEAR).toString()

        exerciseRepository.insertExercise(ExerciseEntity(
            null,
            exerciseName,
            measurementType!!,
            measurementValue,
            creationDate,
            0
        ))

    }

}