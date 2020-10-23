package com.kobietka.trainingtimer.di

import android.content.Context
import com.kobietka.trainingtimer.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class PresentationModule(private val activity: MainActivity) {

    @Provides
    @Named("ActivityContext")
    fun provideActivityContext(): Context {
        return activity
    }

}