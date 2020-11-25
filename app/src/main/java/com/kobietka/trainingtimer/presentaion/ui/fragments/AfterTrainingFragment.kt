package com.kobietka.trainingtimer.presentaion.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.AfterTrainingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_after_training.*
import javax.inject.Inject


@AndroidEntryPoint
class AfterTrainingFragment : BaseFragment() {

    @Inject lateinit var viewModel: AfterTrainingViewModel
    lateinit var navController: NavController
    lateinit var workoutId: String
    lateinit var time: String
    lateinit var repetitions: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workoutId = requireArguments().getString("workoutId")!!
        time = requireArguments().getString("time")!!
        repetitions = requireArguments().getString("repetitions")!!

        viewModel.setTime(time)
        viewModel.setRepetitions(repetitions)
        viewModel.switchWorkoutId(workoutId.toInt())
        viewModel.randomGreeting()

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        viewModel.howManyTimes().observe(viewLifecycleOwner, {
            fragment_after_training_screen_amount_input.text = " $it "
        })

        viewModel.time().observe(viewLifecycleOwner, {
            fragment_after_training_time.text = "Time: $it"
        })

        viewModel.greeting().observe(viewLifecycleOwner, {
            fragment_after_training_screen_greeting_text.text = it
        })

        viewModel.repetitions().observe(viewLifecycleOwner, {
            fragment_after_training_repetitions.text = it
        })

        viewModel.completedGoals().observe(viewLifecycleOwner, {
            fragment_after_training_completed_goals.text = it
        })

        fragment_after_training_screen_fab.setOnClickListener {
            navController.navigate(R.id.action_afterTrainingFragment_to_mainFragment)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_after_training
    }

}