package com.kobietka.trainingtimer.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [WorkoutEntity::class, ExerciseEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
}