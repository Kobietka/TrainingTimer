package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.WorkoutsViewModel


class WorkoutsViewHolder(itemView: View,
                         val viewModel: WorkoutsViewModel,
                         private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {


    fun onAttach(){
        viewModel.name().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_workout_entry_name)
                .text = it
        })

        viewModel.numberOfExercises().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_workout_entry_count)
                .text = it.toString()
        })
    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
        viewModel.numberOfExercises().removeObservers(lifecycleOwner)
    }

}