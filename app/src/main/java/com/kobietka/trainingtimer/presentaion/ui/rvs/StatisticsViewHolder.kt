package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.StatisticsViewModel


class StatisticsViewHolder(itemView: View, val viewModel: StatisticsViewModel, val lifecycleOwner: LifecycleOwner): RecyclerView.ViewHolder(itemView) {


    fun onAttach(){
        viewModel.dataRange().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_statistics_date_range).text = it
        })

        viewModel.workoutCount().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_statistics_workout_count).text = it
        })
    }
    
    fun onDetach(){
        viewModel.dataRange().removeObservers(lifecycleOwner)
        viewModel.workoutCount().removeObservers(lifecycleOwner)
    }

}