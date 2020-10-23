package com.kobietka.trainingtimer.presentaion.common

import androidx.appcompat.app.AppCompatActivity
import com.kobietka.trainingtimer.App
import com.kobietka.trainingtimer.di.PresentationComponent
import com.kobietka.trainingtimer.di.PresentationModule


abstract class BaseActivity : AppCompatActivity() {

    protected val presentationComponent: PresentationComponent by lazy {
        App.applicationComponent.presentationComponent(PresentationModule(activity = this))
    }

}