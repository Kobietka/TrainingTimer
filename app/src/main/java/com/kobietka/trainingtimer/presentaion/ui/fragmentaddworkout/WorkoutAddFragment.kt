package com.kobietka.trainingtimer.presentaion.ui.fragmentaddworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.AddWorkoutUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_workout.*
import javax.inject.Inject


@AndroidEntryPoint
class WorkoutAddFragment : BaseFragment() {

    @Inject lateinit var addWorkoutViewModel: AddWorkoutUIViewModel
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        fragment_create_workout_back_arrow.setOnClickListener {
            navController.navigate(R.id.action_workoutAddFragment_to_workoutsFragment)
        }

        fragment_create_workout_add.setOnClickListener {
            save()
        }

    }

    private fun save(){
        val name = fragment_create_workout_edit_text_name.text.toString()
        val restTime = fragment_create_workout_rest_edit_text.text.toString()

        if(name != "" && restTime != ""){
            addWorkoutViewModel.saveWorkout(name, restTime.toInt())
            navController.navigate(R.id.action_workoutAddFragment_to_workoutsFragment)
        } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_add_workout
    }

}