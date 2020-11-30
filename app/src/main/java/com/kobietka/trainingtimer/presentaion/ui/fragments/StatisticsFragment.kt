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
import com.google.android.material.transition.MaterialSharedAxis
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.StatisticsAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.StatisticsUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import javax.inject.Inject


@AndroidEntryPoint
class StatisticsFragment : BaseFragment() {

    @Inject lateinit var adapter: StatisticsAdapter
    @Inject lateinit var viewModel: StatisticsUIViewModel
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

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkWeeks()

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        adapter.setLifeCycleOwner(viewLifecycleOwner)

        adapter.onItemClick {
            val bundle = bundleOf("weekId" to it.toString())
            navController.navigate(R.id.action_statisticsFragment_to_weekReviewFragment, bundle)
        }

        recyclerView = view.findViewById(R.id.fragment_statistics_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        recyclerView.adapter = adapter

        viewModel.noWeeks().observe(viewLifecycleOwner, {
            if(it){
                fragment_statistics_text_if_no_weeks.visibility = View.GONE
            }
        })

        fragment_statistics_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_statistics
    }

}