package com.kobietka.trainingtimer.presentaion.ui.fragmentexercises

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.ExercisesAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.ExercisesUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject


@AndroidEntryPoint
class ExercisesFragment : BaseFragment() {

    @Inject lateinit var adapter: ExercisesAdapter
    @Inject lateinit var viewModel: ExercisesUIViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        viewModel.loadExercises()

        recyclerView = view.findViewById(R.id.fragment_exercises_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false)

        adapter.setLifeCycleOwner(viewLifecycleOwner)

        adapter.onEditClicks {
            val bundle = bundleOf("exerciseId" to it.toString())
            navController.navigate(R.id.action_exercisesFragment_to_editExerciseFragment, bundle)
        }

        adapter.onDeleteClicks { exerciseId ->
            viewModel.checkIfExerciseIsInWorkouts(exerciseId)
        }

        recyclerView.adapter = adapter

        viewModel.setIsInWorkout { exist, exerciseId ->
            if(!exist){
                viewModel.deleteExercise(exerciseId)
            } else {
                Snackbar.make(view,
                    "Deleting will also delete this exercise from all existing workouts",
                    Snackbar.LENGTH_LONG)
                    .setAction("Delete") { _ ->
                        viewModel.deleteExercise(exerciseId)
                    }.show()
            }
        }

        viewModel.textIfNoExercises().observe(viewLifecycleOwner, {
            if(!it) fragment_exercises_text_if_no_exercises.visibility = View.GONE
        })

        fragment_exercises_back_arrow.setOnClickListener {
            navController.navigate(R.id.action_exercisesFragment_to_mainFragment)
        }

        fragment_exercises_add.setOnClickListener {
            navController.navigate(R.id.action_exercisesFragment_to_exerciseAddFragment)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_exercises
    }

}