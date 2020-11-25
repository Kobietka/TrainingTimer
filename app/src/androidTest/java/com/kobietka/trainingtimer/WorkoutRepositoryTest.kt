package com.kobietka.trainingtimer

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.kobietka.trainingtimer.data.AppDatabase
import com.kobietka.trainingtimer.data.WorkoutEntity
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class WorkoutRepositoryTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var workoutRepository: WorkoutRepository
    private val workoutEntity = WorkoutEntity(
        null,
        "Cardio",
        15,
        "20/9/2020",
        0,
        0
    )

    @Before
    fun init(){
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java).build()
        workoutRepository = WorkoutRepository(appDatabase.workoutDao())
    }

    @After
    fun closeDb(){
        appDatabase.close()
    }

    @Test
    fun insertWorkout_SaveData(){
        workoutRepository.insertWorkout(workoutEntity).subscribe()

        workoutRepository.getAllWorkouts().test().assertValue {
            it.isNotEmpty()
        }
    }

    @Test
    fun insertWorkouts_DeleteAll(){
        val list = listOf(workoutEntity, workoutEntity, workoutEntity)
        list.forEach {
            workoutRepository.insertWorkout(it).subscribe()
        }

        workoutRepository.deleteAllWorkouts().subscribe()

        workoutRepository.getAllWorkouts().test().assertValue {
            it.isEmpty()
        }
    }

    @Test
    fun insertWorkout_DeleteById(){
        workoutRepository.insertWorkout(workoutEntity).flatMapCompletable {
            workoutRepository.deleteById(it.toInt())
        }.test().assertComplete()
    }

    @Test
    fun insertWorkout_GetById(){
        workoutRepository.insertWorkout(workoutEntity).flatMap {
            workoutRepository.getById(it.toInt())
        }.test().assertValue(workoutEntity.copy(id = 1))
    }


}