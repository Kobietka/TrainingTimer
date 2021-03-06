package com.kobietka.trainingtimer.presentaion.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialSharedAxis
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.EditWorkoutAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.EditWorkoutUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_edit_workout.*
import javax.inject.Inject


@AndroidEntryPoint
class EditWorkoutFragment: BaseFragment() {

    @Inject lateinit var editWorkoutViewModel: EditWorkoutUIViewModel
    @Inject lateinit var adapter: EditWorkoutAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    lateinit var workoutId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        workoutId = requireArguments().getString("workoutId")!!
        adapter.setWorkoutId(workoutId.toInt())
        editWorkoutViewModel.switchId(workoutId.toInt())


        recyclerView = view.findViewById(R.id.fragment_edit_workout_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        adapter.setItemClick {
            editWorkoutViewModel.deleteRelation(it)
        }
        recyclerView.adapter = adapter

        fragment_edit_workout_add_exercise.setOnClickListener {
            val name = fragment_edit_workout_edit_text_name.text.toString()
            val restTime = fragment_edit_workout_rest_edit_text.text.toString()

            if(name != "" && restTime != ""){
                editWorkoutViewModel.onSaveClick(name, restTime.toInt())
                val bundle = bundleOf("workoutId" to workoutId)
                navController.navigate(R.id.action_editWorkoutFragment_to_chooseExerciseFragment, bundle)
            } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
        }

        fragment_edit_workout_add.setOnClickListener {
            val name = fragment_edit_workout_edit_text_name.text.toString()
            val restTime = fragment_edit_workout_rest_edit_text.text.toString()

            if(name != "" && restTime != ""){
                editWorkoutViewModel.onSaveClick(name, restTime.toInt())
                requireActivity().onBackPressed()
                //navController.navigate(R.id.action_editWorkoutFragment_to_workoutsFragment)
            } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
        }

        fragment_edit_workout_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
            //navController.navigate(R.id.action_editWorkoutFragment_to_workoutsFragment)
        }

        editWorkoutViewModel.name().observe(viewLifecycleOwner, {
            fragment_edit_workout_edit_text_name.setText(it)
        })

        editWorkoutViewModel.restTime().observe(viewLifecycleOwner, {
            fragment_edit_workout_rest_edit_text.setText(it.toString())
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_edit_workout
    }

}