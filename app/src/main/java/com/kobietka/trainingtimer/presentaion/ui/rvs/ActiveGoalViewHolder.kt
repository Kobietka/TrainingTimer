package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.ActiveGoalViewModel


class ActiveGoalViewHolder(itemView: View, val viewModel: ActiveGoalViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {

    fun onAttach(){
        val progressBar = itemView.findViewById<ProgressBar>(R.id.active_goal_progress_bar)

        viewModel.name().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.active_goal_name).text = it
        })

        viewModel.type().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.active_goal_type).text = it
        })

        viewModel.attached().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.active_goal_attached).text = it
        })

        viewModel.goal().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.active_goal_progress_end).text = it
            progressBar.max = it.toInt()
        })

        viewModel.currentProgress().observe(lifecycleOwner, {
            progressBar.progress = it.toInt()
        })
    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
        viewModel.type().removeObservers(lifecycleOwner)
        viewModel.attached().removeObservers(lifecycleOwner)
        viewModel.goal().removeObservers(lifecycleOwner)
        viewModel.currentProgress().removeObservers(lifecycleOwner)
    }

}