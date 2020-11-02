package com.kobietka.trainingtimer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kobietka.trainingtimer.models.Converters


@Database(entities = [WorkoutEntity::class, ExerciseEntity::class, WorkoutRelation::class], version = 14)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutRelationDao(): WorkoutRelationDao
}