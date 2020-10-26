package com.kobietka.trainingtimer.presentaion.ui.fragmentexercises

import android.os.Bundle
import android.view.View
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import kotlinx.android.synthetic.main.fragment_exercises.*


class ExercisesFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_exercises_back_arrow.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment())
                .commit()
        }

        fragment_exercises_add.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ExerciseAddFragment())
                .commit()
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_exercises
    }

}