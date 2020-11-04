package com.kobietka.trainingtimer.presentaion.ui.fragmenteditworkout

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.ClickId
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.WorkoutAddExerciseEvent
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise.ChooseExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentworkouts.WorkoutsFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.AddWorkoutAdapter
import com.kobietka.trainingtimer.presentaion.ui.rvs.EditWorkoutAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.EditWorkoutUIViewModel
import com.kobietka.trainingtimer.presentaion.viewmodels.EditWorkoutViewModel
import com.mikepenz.fastadapter.dsl.genericFastAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_add_workout.*
import kotlinx.android.synthetic.main.fragment_edit_workout.*
import javax.inject.Inject


class EditWorkoutFragment: BaseFragment() {

    @Inject lateinit var launchEvents: Observable<EventType>
    @Inject lateinit var editWorkoutViewModel: EditWorkoutUIViewModel
    @Inject lateinit var adapter: EditWorkoutAdapter
    @Inject lateinit var workoutIdSender: Subject<Int>
    lateinit var recyclerView: RecyclerView
    private val compositeDisposable = CompositeDisposable()
    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        compositeDisposable.add(
            launchEvents.subscribe {
                if(it.clickId == ClickId.EditWorkout) editWorkoutViewModel.switchId(it.itemId)
                if(it.clickId == ClickId.EditWorkoutFromChoosing) editWorkoutViewModel.switchId(it.itemId)
            }
        )

        recyclerView = view.findViewById(R.id.fragment_edit_workout_rv)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )

        adapter.setLifeCycleOwner(viewLifecycleOwner)
        recyclerView.adapter = adapter

        fragment_edit_workout_add_exercise.setOnClickListener {
            val name = fragment_edit_workout_edit_text_name.text.toString()
            val restTime = fragment_edit_workout_rest_edit_text.text.toString()

            if(name != "" && restTime != ""){
                editWorkoutViewModel.onSaveClick(name, restTime.toInt())
                editWorkoutViewModel.onAddExerciseClick()
            } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
        }

        fragment_edit_workout_add.setOnClickListener {
            val name = fragment_edit_workout_edit_text_name.text.toString()
            val restTime = fragment_edit_workout_rest_edit_text.text.toString()

            if(name != "" && restTime != ""){
                editWorkoutViewModel.onSaveClick(name, restTime.toInt())
                navController.navigate(R.id.action_editWorkoutFragment_to_workoutsFragment)
            } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
        }

        fragment_edit_workout_back_arrow.setOnClickListener {
            requireActivity().onBackPressed()
            compositeDisposable.clear()
        }

        editWorkoutViewModel.name().observe(viewLifecycleOwner, {
            fragment_edit_workout_edit_text_name.setText(it)
        })

        editWorkoutViewModel.restTime().observe(viewLifecycleOwner, {
            fragment_edit_workout_rest_edit_text.setText(it.toString())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        editWorkoutViewModel.compositeDisposable.clear()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_edit_workout
    }

}