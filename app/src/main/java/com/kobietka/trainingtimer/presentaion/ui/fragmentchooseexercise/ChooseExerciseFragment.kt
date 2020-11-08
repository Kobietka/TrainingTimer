package com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.WorkoutAddExerciseEvent
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.ChooseExerciseAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.ChooseExerciseUIViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_choose_exercise.*
import javax.inject.Inject



class ChooseExerciseFragment : BaseFragment() {

    @Inject lateinit var adapter: ChooseExerciseAdapter
    @Inject lateinit var viewModel: ChooseExerciseUIViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    var currentWorkoutId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        currentWorkoutId = requireArguments().getString("workoutId")!!.toInt()
        viewModel.switchWorkoutId(currentWorkoutId)

        fragment_choose_exercise_back_arrow.setOnClickListener {
            val bundle = bundleOf("workoutId" to currentWorkoutId.toString())
            navController.navigate(R.id.action_chooseExerciseFragment_to_editWorkoutFragment, bundle)
        }

        recyclerView = view.findViewById(R.id.fragment_choose_exercise_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        adapter.setOnAddClickFunction {
            viewModel.switchExerciseId(it)
            val bundle = bundleOf("workoutId" to currentWorkoutId.toString())
            navController.navigate(R.id.action_chooseExerciseFragment_to_editWorkoutFragment, bundle)
        }
        recyclerView.adapter = adapter

    }

    override fun getLayout(): Int {
        return R.layout.fragment_choose_exercise
    }

}