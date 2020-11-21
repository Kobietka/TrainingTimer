package com.kobietka.trainingtimer.presentaion.ui.fragmentcreategoal

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.CreateGoalUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_goal.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateGoalFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: CreateGoalUIViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_create_goal_add.setOnClickListener {
            val radioButton = view.findViewById<RadioButton>(fragment_create_goal_radio_group.checkedRadioButtonId)

            val type = when(radioButton.text){
                "Time" -> MeasurementType.Time
                "Repetition" -> MeasurementType.Repetition
                else -> null
            }
            viewModel.saveGoal(fragment_create_goal_edit_text_name.text.toString(),
                fragment_create_goal_value_edit_text.text.toString().toInt(), type!!)

            requireActivity().onBackPressed()
        }

        fragment_create_goal_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_add_goal
    }

}