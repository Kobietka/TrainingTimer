package com.kobietka.trainingtimer.presentaion.ui.fragmenttrainingscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.TrainingScreenViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_training_screen.*
import javax.inject.Inject


class TrainingScreenFragment : BaseFragment() {

    @Inject lateinit var viewModel: TrainingScreenViewModel
    lateinit var navController: NavController

    var workoutId = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        workoutId = requireArguments().getString("workoutId")!!.toInt()
        viewModel.switchWorkoutId(workoutId)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        viewModel.exerciseName().observe(viewLifecycleOwner, {
            fragment_training_screen_exercise_text.text = it
        })

        viewModel.exerciseNumber().observe(viewLifecycleOwner, {
            fragment_training_screen_exercise_count.text = it
        })

        viewModel.timeOrRep().observe(viewLifecycleOwner, {
            fragment_training_screen_time_or_repetition.text = it
        })

        viewModel.nextExercise().observe(viewLifecycleOwner, {
            fragment_training_screen_next_exercise.text = it
        })

        viewModel.fabGone().observe(viewLifecycleOwner, {
            if(it) fragment_training_screen_fab.visibility = View.GONE
            else fragment_training_screen_fab.visibility = View.VISIBLE
        })

        viewModel.playOrPause().observe(viewLifecycleOwner, {
            if(it){
                val play = resources.getDrawable(R.drawable.ic_play_arrow_fab)
                fragment_training_screen_fab.setImageDrawable(play)
            } else {
                val pause = resources.getDrawable(R.drawable.ic_pause)
                fragment_training_screen_fab.setImageDrawable(pause)
            }
        })

        viewModel.onTrainingEnd { time, workoutId ->
            val bundle = bundleOf("time" to time, "workoutId" to workoutId)
            navController.navigate(R.id.action_trainingScreenFragment_to_afterTrainingFragment, bundle)
        }

        viewModel.onWorkoutZeroExercises {
            Toast.makeText(activity, "Cannot start while workout has 0 exercises", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_trainingScreenFragment_to_trainFragment)
        }

        fragment_training_screen_fab.setOnClickListener {
            viewModel.onFabClick()
        }

        fragment_training_screen_back_arrow.setOnClickListener {
            viewModel.clearComposite()
            navController.navigate(R.id.action_trainingScreenFragment_to_trainFragment)
        }

    }

    override fun onDetach() {
        super.onDetach()
        viewModel.clearComposite()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_training_screen
    }

}