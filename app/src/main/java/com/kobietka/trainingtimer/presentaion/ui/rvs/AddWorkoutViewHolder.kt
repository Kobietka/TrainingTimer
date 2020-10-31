package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.viewmodels.AddWorkoutViewModel
import com.kobietka.trainingtimer.presentaion.viewmodels.ExerciseViewModel


class AddWorkoutViewHolder(itemView: View, val viewModel: AddWorkoutViewModel,
                           private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {

    fun onAttach(){
        viewModel.measurementType().observe(lifecycleOwner, Observer {
            itemView.findViewById<TextView>(R.id.fragment_create_workout_entry_measurement_type_text)
                .text = when (it) {
                MeasurementType.Repetition -> "reps"
                MeasurementType.Time -> "seconds"
            }
        })

        viewModel.name().observe(lifecycleOwner, Observer {
            itemView.findViewById<TextView>(R.id.fragment_create_workout_entry_name)
                .text = it
        })

        viewModel.measurementValue().observe(lifecycleOwner, Observer {
            itemView.findViewById<TextView>(R.id.fragment_create_workout_entry_count)
                .text = it.toString()
        })
    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
        viewModel.measurementValue().removeObservers(lifecycleOwner)
        viewModel.measurementType().removeObservers(lifecycleOwner)
    }

}