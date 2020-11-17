package com.kobietka.trainingtimer.presentaion.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kobietka.trainingtimer.App
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    abstract fun getLayout(): Int

}