package com.kobietka.trainingtimer.presentaion.ui.fragmentworkouts

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddworkout.WorkoutAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.WorkoutsAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.WorkoutsUIViewModel
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_workouts.*
import javax.inject.Inject


class WorkoutsFragment : BaseFragment() {

    @Inject lateinit var adapter: WorkoutsAdapter
    @Inject lateinit var viewModel: WorkoutsUIViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        viewModel.loadWorkouts()

        recyclerView = view.findViewById(R.id.fragment_workouts_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)

        adapter.onEditClicks {
            val bundle = bundleOf("workoutId" to it.toString())
            navController.navigate(R.id.action_workoutsFragment_to_editWorkoutFragment, bundle)
        }

        adapter.onDeleteClicks { workoutId ->
            Snackbar.make(view, "Delete this workout? This action is not reversible", Snackbar.LENGTH_LONG)
                .setAction("DELETE") {
                    viewModel.deleteWorkout(workoutId)
                }.show()
        }
        recyclerView.adapter = adapter

        viewModel.textIfNoWorkouts().observe(viewLifecycleOwner, {
            if(!it) fragment_workouts_text_if_no_exercises.visibility = View.GONE
        })

        fragment_workouts_add.setOnClickListener {
            navController.navigate(R.id.action_workoutsFragment_to_workoutAddFragment)
        }

        fragment_workouts_back_arrow.setOnClickListener {
            navController.navigate(R.id.action_workoutsFragment_to_mainFragment)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_workouts
    }

}
