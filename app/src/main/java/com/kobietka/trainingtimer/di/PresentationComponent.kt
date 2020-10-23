package com.kobietka.trainingtimer.di

import com.kobietka.trainingtimer.presentaion.ui.MainActivity
import dagger.Subcomponent


@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(mainActivity: MainActivity)
}