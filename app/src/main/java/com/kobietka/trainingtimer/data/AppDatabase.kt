package com.kobietka.trainingtimer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kobietka.trainingtimer.models.Converters


@Database(entities = [WorkoutEntity::class, ExerciseEntity::class,
    WorkoutRelation::class, HistoryEntity::class,
    ActiveGoal::class, CompletedGoal::class,
    CompletedWorkoutEntity::class, WeekEntity::class], version = 21)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutRelationDao(): WorkoutRelationDao
    abstract fun historyDao(): HistoryDao
    abstract fun activeGoalDao(): ActiveGoalDao
    abstract fun completedGoalDao(): CompletedGoalDao
    abstract fun completedWorkoutDao(): CompletedWorkoutDao
    abstract fun weekDao(): WeekDao
}