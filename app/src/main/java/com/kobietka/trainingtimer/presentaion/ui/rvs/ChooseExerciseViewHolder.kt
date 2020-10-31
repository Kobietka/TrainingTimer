package com.kobietka.trainingtimer.presentaion.ui.rvs

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.viewmodels.ChooseExerciseViewModel


class ChooseExerciseViewHolder(itemView: View,
                               val viewModel: ChooseExerciseViewModel,
                               private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {


    fun onAttach(){
        viewModel.measurementType().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_choose_exercise_entry_measurement_type_text)
                .text = when (it) {
                MeasurementType.Repetition -> "reps"
                MeasurementType.Time -> "seconds"
            }
        })

        viewModel.name().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_choose_exercise_entry_name)
                .text = it
        })

        viewModel.measurementValue().observe(lifecycleOwner, {
            itemView.findViewById<TextView>(R.id.fragment_choose_exercise_entry_count)
                .text = it.toString()
        })
    }

    fun onDetach(){
        viewModel.name().removeObservers(lifecycleOwner)
        viewModel.measurementValue().removeObservers(lifecycleOwner)
    }


}