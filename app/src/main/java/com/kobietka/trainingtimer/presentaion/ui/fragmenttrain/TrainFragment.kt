package com.kobietka.trainingtimer.presentaion.ui.fragmenttrain

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.TrainAdapter
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_train.*
import javax.inject.Inject


@AndroidEntryPoint
class TrainFragment : BaseFragment() {

    @Inject lateinit var adapter: TrainAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

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

        fragment_train_back_arrow.setOnClickListener {
            navController.navigate(R.id.action_trainFragment_to_mainFragment)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_train
    }

}