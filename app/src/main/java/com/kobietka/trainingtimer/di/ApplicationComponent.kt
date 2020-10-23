package com.kobietka.trainingtimer.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DatabaseModule::class])
interface ApplicationComponent {
    fun presentationComponent(presentationModule: PresentationModule): PresentationComponent
}