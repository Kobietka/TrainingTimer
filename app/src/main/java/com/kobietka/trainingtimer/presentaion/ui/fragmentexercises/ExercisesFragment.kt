package com.kobietka.trainingtimer.presentaion.ui.fragmentexercises

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.ExercisesAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.ExerciseViewModel
import com.kobietka.trainingtimer.presentaion.viewmodels.ExercisesUIViewModel
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject


class ExercisesFragment : BaseFragment() {

    @Inject lateinit var adapter: ExercisesAdapter
    @Inject lateinit var viewModel: ExercisesUIViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

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

        adapter.onDeleteClicks {
            viewModel.deleteExercise(it)
        }

        recyclerView.adapter = adapter

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