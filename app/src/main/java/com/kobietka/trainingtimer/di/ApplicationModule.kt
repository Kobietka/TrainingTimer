package com.kobietka.trainingtimer.di

import android.app.Application
import android.content.Context
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.WorkoutAddExerciseEvent
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {
    
    @Provides
    @Named("ApplicationContext")
    fun provideApplicationContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideEventSubject(): Subject<EventType> {
        return BehaviorSubject.create<EventType>().toSerialized()
    }

    @Singleton
    @Provides
    fun provideWorkoutEvents(): Subject<WorkoutAddExerciseEvent> {
        return BehaviorSubject.create<WorkoutAddExerciseEvent>().toSerialized()
    }

    @Singleton
    @Provides
    fun provideWorkoutIdSender(): Subject<Int> {
        return BehaviorSubject.create<Int>().toSerialized()
    }

    
}