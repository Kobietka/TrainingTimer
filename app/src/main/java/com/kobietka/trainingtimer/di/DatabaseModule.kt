package com.kobietka.trainingtimer.di

import android.content.Context
import androidx.room.Room
import com.kobietka.trainingtimer.data.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@Named("ApplicationContext") context: Context): AppDatabase {
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

}