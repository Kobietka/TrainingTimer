package com.kobietka.trainingtimer.di

import com.kobietka.trainingtimer.data.ActiveGoalDao
import com.kobietka.trainingtimer.data.CompletedGoalDao
import com.kobietka.trainingtimer.data.ExerciseDao
import com.kobietka.trainingtimer.data.WorkoutDao
import com.kobietka.trainingtimer.repositories.ActiveGoalRepository
import com.kobietka.trainingtimer.repositories.CompletedGoalRepository
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideExerciseRepository(exerciseDao: ExerciseDao): ExerciseRepository {
        return ExerciseRepository(exerciseDao)
    }

    @Provides
    @Singleton
    fun provideWorkoutRepository(workoutDao: WorkoutDao): WorkoutRepository {
        return WorkoutRepository(workoutDao)
    }

    @Provides
    @Singleton
    fun provideActiveGoalRepository(activeGoalDao: ActiveGoalDao): ActiveGoalRepository {
        return ActiveGoalRepository(activeGoalDao)
    }

    @Provides
    @Singleton
    fun provideCompletedGoalRepository(completedGoalDao: CompletedGoalDao): CompletedGoalRepository {
        return CompletedGoalRepository(completedGoalDao)
    }

}