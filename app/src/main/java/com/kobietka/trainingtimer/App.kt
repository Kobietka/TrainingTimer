package com.kobietka.trainingtimer

import android.app.Application
import com.kobietka.trainingtimer.di.ApplicationComponent
import com.kobietka.trainingtimer.di.ApplicationModule
import com.kobietka.trainingtimer.di.DaggerApplicationComponent

class App : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

}