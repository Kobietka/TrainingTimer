package com.kobietka.trainingtimer.presentaion.ui.fragmenteditgoal

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.AttachedWorkoutAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.EditGoalUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_goal.*
import kotlinx.android.synthetic.main.fragment_edit_goal.*
import javax.inject.Inject


@AndroidEntryPoint
class EditGoalFragment : BaseFragment() {

    @Inject lateinit var viewModel: EditGoalUIViewModel
    @Inject lateinit var adapter: AttachedWorkoutAdapter
    lateinit var recyclerView: RecyclerView

    var currentGoalId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentGoalId = requireArguments().getString("goalId")!!.toInt()
        viewModel.switchGoalId(currentGoalId)

        adapter.setLifeCycleOwner(viewLifecycleOwner)

        recyclerView = view.findViewById(R.id.fragment_edit_goal_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setOnClick {
            viewModel.setCurrentWorkout(it)
        }

        recyclerView.adapter = adapter

        fragment_edit_goal_add.setOnClickListener {
            val goalName = fragment_edit_goal_edit_text_name.text.toString()
            val goalValue = fragment_edit_goal_value_edit_text.text.toString()
            val radioButtonId = fragment_edit_goal_radio_group.checkedRadioButtonId

            if(goalName.isNotEmpty() && goalValue.isNotEmpty() && radioButtonId != -1){
                val type = when(view.findViewById<RadioButton>(radioButtonId).text){
                    "Time" -> MeasurementType.Time
                    "Repetition" -> MeasurementType.Repetition
                    else -> null
                }
                viewModel.saveGoal(goalName, goalValue.toInt(), type!!)

                requireActivity().onBackPressed()
            } else Snackbar.make(view, "Please fill all fields", Snackbar.LENGTH_LONG).show()
        }

        fragment_edit_goal_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
        }

        fragment_edit_goal_delete_workout.setOnClickListener {
            viewModel.onWorkoutRemoved()
        }

        viewModel.name().observe(viewLifecycleOwner, {
            view.findViewById<EditText>(R.id.fragment_edit_goal_edit_text_name).setText(it)
        })

        viewModel.removeWorkout().observe(viewLifecycleOwner, {
            view.findViewById<ImageView>(R.id.fragment_edit_goal_delete_workout)
                .visibility = if(it) View.VISIBLE else View.GONE
        })

        viewModel.goalValue().observe(viewLifecycleOwner, {
            view.findViewById<EditText>(R.id.fragment_edit_goal_value_edit_text).setText(it)
        })

        viewModel.goalType().observe(viewLifecycleOwner, {
            when (it){
                MeasurementType.Repetition -> view.findViewById<RadioButton>(R.id.fragment_edit_goal_radio_button_repetition).isChecked = true
                MeasurementType.Time -> view.findViewById<RadioButton>(R.id.fragment_edit_goal_radio_button_time).isChecked = true
            }
        })

        viewModel.attachedWorkout().observe(viewLifecycleOwner, {
            view.findViewById<TextView>(R.id.fragment_edit_goal_anchor_text).text = it
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_edit_goal
    }

}