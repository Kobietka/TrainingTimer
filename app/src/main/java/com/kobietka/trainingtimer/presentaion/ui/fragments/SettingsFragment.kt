package com.kobietka.trainingtimer.presentaion.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.SettingsUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    @Inject lateinit var viewModel: SettingsUIViewModel

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

        fragment_settings_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }

        fragment_settings_clear_history.setOnClickListener {
            Snackbar.make(view, "Confirm to delete history", Snackbar.LENGTH_LONG)
                .setAction("DELETE") {
                    viewModel.onDeleteHistory()
                    Snackbar.make(view, "History deleted", Snackbar.LENGTH_LONG).show()
                }.show()
        }

        fragment_settings_clear_completed_goals.setOnClickListener {
            Snackbar.make(view, "Confirm to delete completed goals", Snackbar.LENGTH_LONG)
                .setAction("DELETE") {
                    viewModel.onDeleteCompletedGoals()
                    Snackbar.make(view, "Completed goals deleted", Snackbar.LENGTH_LONG).show()
                }.show()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_settings
    }
}