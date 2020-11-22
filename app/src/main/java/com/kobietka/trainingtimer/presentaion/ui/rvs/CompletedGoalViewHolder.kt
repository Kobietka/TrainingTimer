package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.CompletedGoalViewModel


class CompletedGoalViewHolder(itemView: View, val viewModel: CompletedGoalViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {

    fun onAttach(){
        viewModel.name().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.completed_goal_name).text = it
        })

        viewModel.type().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.completed_goal_type).text = it
        })

        viewModel.attached().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.completed_goal_attached).text = it
        })

        viewModel.goal().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.completed_goal_value).text = "Goal: $it"
        })

        viewModel.creationDate().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.completed_goal_creation_date).text = "Creation date: $it"
        })

        viewModel.completionDate().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.completed_goal_completion_date).text = "Completion date: $it"
        })

    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
        viewModel.type().removeObservers(lifecycleOwner)
        viewModel.attached().removeObservers(lifecycleOwner)
        viewModel.goal().removeObservers(lifecycleOwner)
        viewModel.creationDate().removeObservers(lifecycleOwner)
        viewModel.completionDate().removeObservers(lifecycleOwner)
    }

}