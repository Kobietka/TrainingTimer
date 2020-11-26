package com.kobietka.trainingtimer.di

import android.content.Context
import androidx.room.Room
import com.kobietka.trainingtimer.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideWorkoutDao(appDatabase: AppDatabase): WorkoutDao {
        return appDatabase.workoutDao()
    }

    @Provides
    fun provideExerciseDao(appDatabase: AppDatabase): ExerciseDao {
        return appDatabase.exerciseDao()
    }

    @Provides
    fun provideWorkoutRelationDao(appDatabase: AppDatabase): WorkoutRelationDao {
        return appDatabase.workoutRelationDao()
    }

    @Provides
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
        return appDatabase.historyDao()
    }

    @Provides
    fun provideActiveGoalDao(appDatabase: AppDatabase): ActiveGoalDao {
        return appDatabase.activeGoalDao()
    }

    @Provides
    fun provideCompletedGoalDao(appDatabase: AppDatabase): CompletedGoalDao {
        return appDatabase.completedGoalDao()
    }

    @Provides
    fun provideCompletedWorkoutDao(appDatabase: AppDatabase): CompletedWorkoutDao {
        return appDatabase.completedWorkoutDao()
    }

}