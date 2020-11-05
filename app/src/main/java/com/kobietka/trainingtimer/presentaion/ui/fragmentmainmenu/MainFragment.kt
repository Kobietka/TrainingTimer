package com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentexercises.ExercisesFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentworkouts.WorkoutsFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        fragment_main_train_element.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_trainFragment)
        }

        fragment_main_workouts_elements.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_workoutsFragment)
        }

        fragment_main_exercises_element.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_exercisesFragment)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_main
    }

}