package com.kobietka.trainingtimer.presentaion.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        fragment_main_train_element.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                view.findViewById<FrameLayout>(R.id.fragment_main_train_element) to "fragment_main_train"
            )
            navController.navigate(R.id.action_mainFragment_to_trainFragment, null, null, extras)
        }

        fragment_main_workouts_element.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                view.findViewById<FrameLayout>(R.id.fragment_main_workouts_element) to "fragment_main_workouts"
            )
            navController.navigate(R.id.action_mainFragment_to_workoutsFragment, null, null, extras)
        }

        fragment_main_exercises_element.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                view.findViewById<FrameLayout>(R.id.fragment_main_exercises_element) to "fragment_main_exercises"
            )
            navController.navigate(R.id.action_mainFragment_to_exercisesFragment, null, null, extras)
        }

        fragment_main_history_element.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                view.findViewById<FrameLayout>(R.id.fragment_main_history_element) to "fragment_main_history"
            )
            navController.navigate(R.id.action_mainFragment_to_historyFragment, null, null, extras)
        }

        fragment_main_goals_element.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                view.findViewById<FrameLayout>(R.id.fragment_main_goals_element) to "fragment_main_goals"
            )
            navController.navigate(R.id.action_mainFragment_to_goalsFragment, null, null, extras)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_main
    }

}