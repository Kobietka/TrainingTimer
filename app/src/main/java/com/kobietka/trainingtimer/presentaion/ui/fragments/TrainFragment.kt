package com.kobietka.trainingtimer.presentaion.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialContainerTransform
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.TrainAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.TrainUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_train.*
import javax.inject.Inject


@AndroidEntryPoint
class TrainFragment : BaseFragment() {

    @Inject lateinit var adapter: TrainAdapter
    @Inject lateinit var viewModel: TrainUIViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 500
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(resources.getColor(R.color.white))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        viewModel.checkWorkouts()

        recyclerView = view.findViewById(R.id.fragment_train_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        adapter.setPlayClicks {
            val bundle = bundleOf("workoutId" to it.toString())
            navController.navigate(R.id.action_trainFragment_to_trainingScreenFragment, bundle)
        }
        recyclerView.adapter = adapter

        viewModel.ifNoExercises().observe(viewLifecycleOwner, {
            if(!it){
                fragment_train_text_if_no_exercises.visibility = View.GONE
                fragment_train_image_if_no_exercises.visibility = View.GONE
            }
        })

        fragment_train_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
            //navController.navigate(R.id.action_trainFragment_to_mainFragment)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_train
    }

}