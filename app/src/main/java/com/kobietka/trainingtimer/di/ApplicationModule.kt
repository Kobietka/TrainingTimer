package com.kobietka.trainingtimer.di

import android.app.Application
import android.content.Context
import com.kobietka.trainingtimer.models.EventType
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

    
}