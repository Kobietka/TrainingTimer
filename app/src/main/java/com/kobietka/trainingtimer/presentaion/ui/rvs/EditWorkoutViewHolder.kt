package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.viewmodels.EditWorkoutViewModel

class EditWorkoutViewHolder(itemView: View, val viewModel: EditWorkoutViewModel,
                            private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(itemView) {

    fun onAttach(){
        viewModel.exerciseName().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_edit_workout_entry_name)
                .text = it
        })

        viewModel.exerciseValue().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_edit_workout_entry_count)
                .text = it.toString()
        })

        viewModel.measurementType().observe(lifecycleOwner,  {
            itemView.findViewById<TextView>(R.id.fragment_edit_workout_entry_measurement_type_text)
                .text = when (it) {
                MeasurementType.Repetition -> "reps"
                MeasurementType.Time -> "seconds"
            }
        })
    }

    fun onDetach(){
        viewModel.exerciseName().removeObservers(lifecycleOwner)
        viewModel.exerciseValue().removeObservers(lifecycleOwner)
        viewModel.measurementType().removeObservers(lifecycleOwner)
    }

}