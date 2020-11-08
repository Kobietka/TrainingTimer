package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.HistoryViewModel


class HistoryViewHolder(itemView: View, val viewModel: HistoryViewModel, val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {


    fun onAttach(){
        viewModel.name().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_history_entry_name)
                .text = it
        })

        viewModel.completeDate().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_history_entry_date)
                .text = it
        })
    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
        viewModel.completeDate().removeObservers(lifecycleOwner)
    }

}