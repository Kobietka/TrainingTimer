package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.viewmodels.ExerciseViewModel
import io.reactivex.disposables.CompositeDisposable

class ExerciseViewHolder(itemView: View, val viewModel: ExerciseViewModel, private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {

    fun onAttach(){
        viewModel.name().observe(lifecycleOwner, Observer {
            itemView.findViewById<TextView>(R.id.fragment_exercises_entry_name)
                .text = it
        })

        viewModel.measurementValue().observe(lifecycleOwner, Observer {
            itemView.findViewById<TextView>(R.id.fragment_exercises_entry_count)
                .text = it.toString()
        })
    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
        viewModel.measurementValue().removeObservers(lifecycleOwner)
    }

}