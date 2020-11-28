package com.kobietka.trainingtimer.presentaion.ui.fragments

import android.os.Bundle
import android.view.View
import com.google.android.material.transition.MaterialSharedAxis
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.WeekReviewUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_week_review.*
import javax.inject.Inject

@AndroidEntryPoint
class WeekReviewFragment : BaseFragment() {

    @Inject lateinit var viewModel: WeekReviewUIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dataRange().observe(viewLifecycleOwner, {
            fragment_week_review_date_range.text = it
        })

        viewModel.numberOfRepetitions().observe(viewLifecycleOwner, {
            fragment_week_review_repetition_number.text = it
        })

        viewModel.time().observe(viewLifecycleOwner, {
            fragment_week_review_time_number.text = it
        })

        viewModel.workoutsContent().observe(viewLifecycleOwner, {
            fragment_week_review_workouts_content.text = it
        })

        viewModel.goalsContent().observe(viewLifecycleOwner, {
            fragment_week_review_completed_goals_content.text = it
        })

        viewModel.areGoalsPresent().observe(viewLifecycleOwner, {
            if(it){
                fragment_week_review_completed_goals_text.visibility = View.VISIBLE
                fragment_week_review_completed_goals_content.visibility = View.VISIBLE
            }
        })

        fragment_statistics_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_week_review
    }

}