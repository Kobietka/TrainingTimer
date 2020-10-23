package com.kobietka.trainingtimer.presentaion.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kobietka.trainingtimer.App
import com.kobietka.trainingtimer.di.PresentationComponent
import com.kobietka.trainingtimer.di.PresentationModule


abstract class BaseFragment : Fragment() {

    val presentationComponent: PresentationComponent by lazy {
        App.applicationComponent.presentationComponent(PresentationModule(baseFragment = this))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    abstract fun getLayout(): Int

}