package com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu

import android.os.Bundle
import android.view.View
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentexercises.ExercisesFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        fragment_main_exercises_element.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ExercisesFragment())
                .commit()
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_main
    }

}