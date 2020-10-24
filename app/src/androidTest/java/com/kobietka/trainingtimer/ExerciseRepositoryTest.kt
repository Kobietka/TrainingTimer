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
        30,
        MeasurementType.Time,
        10,
        Calendar.getInstance().time.toString(),
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
    fun insertExercises_GetCountByWorkoutId(){
        val list = listOf(exerciseEntity, exerciseEntity, exerciseEntity)
        list.forEach {
            exerciseRepository.insertExercise(it).subscribe()
        }

        exerciseRepository.getExerciseCountByWorkoutId(exerciseEntity.workoutId!!).test().assertValue {
            it == list.size
        }
    }

    @Test
    fun insertExercise_GetById(){
        exerciseRepository.insertExercise(exerciseEntity).flatMap {
            exerciseRepository.getById(it.toInt())
        }.test().assertValue(exerciseEntity.copy(id = 1))
    }

}