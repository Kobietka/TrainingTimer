package com.kobietka.trainingtimer.presentaion.ui.fragmentworkouts

import android.os.Bundle
import android.view.View
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import kotlinx.android.synthetic.main.fragment_workouts.*


class WorkoutsFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        fragment_workouts_back_arrow.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment())
                .commit()
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_workouts
    }

}
