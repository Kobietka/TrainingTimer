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
import com.kobietka.trainingtimer.presentaion.ui.rvs.ActiveGoalAdapter
import com.kobietka.trainingtimer.presentaion.ui.rvs.CompletedGoalAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_goals.*
import javax.inject.Inject

@AndroidEntryPoint
class GoalsFragment : BaseFragment() {

    @Inject lateinit var activeAdapter: ActiveGoalAdapter
    @Inject lateinit var completedAdapter: CompletedGoalAdapter
    private lateinit var activeRecyclerView: RecyclerView
    private lateinit var completedRecyclerView: RecyclerView
    private lateinit var navController: NavController

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

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        activeAdapter.setLifeCycleOwner(viewLifecycleOwner)

        activeRecyclerView = view.findViewById(R.id.fragment_goals_active_rv)
        activeRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        activeAdapter.setOnItemClick { id ->
            val bundle = bundleOf("goalId" to id.toString())
            navController.navigate(R.id.action_goalsFragment_to_editGoalFragment, bundle)
        }

        activeRecyclerView.adapter = activeAdapter

        completedAdapter.setLifeCycleOwner(viewLifecycleOwner)
        completedRecyclerView = view.findViewById(R.id.fragment_goals_completed_rv)
        completedRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )
        completedRecyclerView.adapter = completedAdapter

        fragment_goals_active_button.setOnClickListener {
            fragment_goals_completed_button.background = resources.getDrawable(R.drawable.list_element_not_activated)
            it.background = resources.getDrawable(R.drawable.list_element)
            activeRecyclerView.visibility = View.VISIBLE
            completedRecyclerView.visibility = View.GONE
        }

        fragment_goals_completed_button.setOnClickListener {
            fragment_goals_active_button.background = resources.getDrawable(R.drawable.list_element_not_activated)
            it.background = resources.getDrawable(R.drawable.list_element)
            activeRecyclerView.visibility = View.GONE
            completedRecyclerView.visibility = View.VISIBLE
        }

        fragment_goals_add.setOnClickListener {
            navController.navigate(R.id.action_goalsFragment_to_createGoalFragment)
        }

        fragment_goals_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_goals
    }

}