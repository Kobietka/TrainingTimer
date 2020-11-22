package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.AttachedWorkoutViewModel

class AttachedWorkoutViewHolder(itemView: View,
                                val viewModel: AttachedWorkoutViewModel,
                                val lifecycleOwner: LifecycleOwner): RecyclerView.ViewHolder(itemView) {

    fun onAttach(){
        viewModel.name().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_create_goal_name).text = it
        })
    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
    }

}