package com.kobietka.trainingtimer

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.kobietka.trainingtimer.data.AppDatabase
import com.kobietka.trainingtimer.data.ExerciseDao
import com.kobietka.trainingtimer.data.ExerciseEntity
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
open class ExerciseRepositoryTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var exerciseRepository : ExerciseRepository
    private val exerciseEntity = ExerciseEntity(
        null,
        "Jumping rope",
        MeasurementType.Time,
        30,
        "26/9/2020",
        0
    )

    @Before
    fun init(){
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java).build()
        exerciseRepository = ExerciseRepository(appDatabase.exerciseDao())
    }

    @After
    fun closeDb(){
        appDatabase.close()
    }

    @Test
    fun insertExercise_SavesData(){
        exerciseRepository.insertExercise(exerciseEntity).subscribe()

        exerciseRepository.getAllExercises().test().assertValue {
            it.isNotEmpty()
        }
    }

    @Test
    fun insertExercises_DeleteAll(){
        val list = listOf(exerciseEntity, exerciseEntity, exerciseEntity)
        list.forEach {
            exerciseRepository.insertExercise(it).subscribe()
        }

        exerciseRepository.deleteAllExercises().subscribe()

       exerciseRepository.getAllExercises().test().assertValue {
            it.isEmpty()
        }
    }

    @Test
    fun insertExercise_DeleteById(){
        exerciseRepository.insertExercise(exerciseEntity).flatMapCompletable {
            exerciseRepository.deleteById(it.toInt())
        }.test().assertComplete()
    }

    @Test
    fun insertExercise_GetById(){
        exerciseRepository.insertExercise(exerciseEntity).flatMap {
            exerciseRepository.getById(it.toInt())
        }.test().assertValue(exerciseEntity.copy(id = 1))
    }

    @Test
    fun insertExercise_UpdateName(){
        val newName = "Push-ups"

        exerciseRepository.insertExercise(exerciseEntity).flatMapCompletable {
            exerciseRepository.updateName(it.toInt(), newName)
        }.subscribe()

        exerciseRepository.getById(1).test().assertValue {
            it.name == newName
        }
    }

    @Test
    fun insertExercise_UpdateMeasurementType(){
        val newType = MeasurementType.Repetition

        exerciseRepository.insertExercise(exerciseEntity).flatMapCompletable {
            exerciseRepository.updateMeasurementType(it.toInt(), newType)
        }.subscribe()

        exerciseRepository.getById(1).test().assertValue {
            it.measurementType == newType
        }
    }

    @Test
    fun insertExercise_UpdateMeasurementValue(){
        val newValue = 15

        exerciseRepository.insertExercise(exerciseEntity).flatMapCompletable {
            exerciseRepository.updateMeasurementValue(it.toInt(), newValue)
        }.subscribe()

        exerciseRepository.getById(1).test().assertValue {
            it.measurementValue == newValue
        }
    }
}