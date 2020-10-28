package com.kobietka.trainingtimer.presentaion.ui.fragmentaddworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentworkouts.WorkoutsFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.AddWorkoutAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.AddWorkoutViewModel
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import kotlinx.android.synthetic.main.fragment_add_workout.*
import javax.inject.Inject


class WorkoutAddFragment : BaseFragment() {

    @Inject lateinit var adapter: AddWorkoutAdapter
    @Inject lateinit var addWorkoutViewModel: AddWorkoutViewModel
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        recyclerView = view.findViewById(R.id.fragment_create_workout_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        recyclerView.adapter = adapter

        fragment_create_workout_back_arrow.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, WorkoutsFragment())
                .commit()
        }

        fragment_create_workout_add.setOnClickListener {
            val name = fragment_create_workout_edit_text_name.text.toString()
            val restTime = fragment_create_workout_rest_edit_text.text.toString()

            if(name != "" && restTime != ""){
                addWorkoutViewModel.saveWorkout(name, restTime.toInt())
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, WorkoutsFragment())
                    .commit()
            } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_add_workout
    }

}