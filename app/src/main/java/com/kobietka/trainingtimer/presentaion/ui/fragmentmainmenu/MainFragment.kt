package com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu

import android.os.Bundle
import android.view.View
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment

class MainFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_main
    }

}