package com.kobietka.trainingtimer.presentaion.ui.fragmentgoals

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.material.transition.MaterialContainerTransform
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_goals.*

@AndroidEntryPoint
class GoalsFragment : BaseFragment() {

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

        fragment_goals_active_button.setOnClickListener {
            fragment_goals_completed_button.background = resources.getDrawable(R.drawable.list_element_not_activated)
            it.background = resources.getDrawable(R.drawable.list_element)
        }

        fragment_goals_completed_button.setOnClickListener {
            fragment_goals_active_button.background = resources.getDrawable(R.drawable.list_element_not_activated)
            it.background = resources.getDrawable(R.drawable.list_element)
        }

        fragment_goals_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_goals
    }

}