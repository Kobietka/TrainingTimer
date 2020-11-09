package com.kobietka.trainingtimer.presentaion.ui.fragmentaftertraining

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.AfterTrainingViewModel
import kotlinx.android.synthetic.main.fragment_after_training.*
import javax.inject.Inject


class AfterTrainingFragment : BaseFragment() {

    @Inject lateinit var viewModel: AfterTrainingViewModel
    lateinit var navController: NavController
    lateinit var workoutId: String
    lateinit var time: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        workoutId = requireArguments().getString("workoutId")!!
        time = requireArguments().getString("time")!!

        viewModel.switchWorkoutId(workoutId.toInt())
        viewModel.setTime(time)
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

        fragment_after_training_screen_fab.setOnClickListener {
            navController.navigate(R.id.action_afterTrainingFragment_to_mainFragment)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_after_training
    }

}