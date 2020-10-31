package com.kobietka.trainingtimer.presentaion.ui.fragmenteditworkout

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise.ChooseExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.rvs.AddWorkoutAdapter
import com.kobietka.trainingtimer.presentaion.viewmodels.EditWorkoutViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_add_workout.*
import kotlinx.android.synthetic.main.fragment_edit_workout.*
import javax.inject.Inject


class EditWorkoutFragment: BaseFragment() {

    @Inject lateinit var launchEvents: Subject<EventType>
    @Inject lateinit var editWorkoutViewModel: EditWorkoutViewModel
    @Inject lateinit var adapter: AddWorkoutAdapter
    lateinit var recyclerView: RecyclerView
    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        compositeDisposable.add(
            launchEvents.subscribe {
                editWorkoutViewModel.loadWorkout(it.itemId)
                editWorkoutViewModel.setId(it.itemId)
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
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ChooseExerciseFragment())
                .commit()
        }

        editWorkoutViewModel.name().observe(viewLifecycleOwner, {
            fragment_edit_workout_edit_text_name.setText(it)
        })

        editWorkoutViewModel.restTime().observe(viewLifecycleOwner, {
            fragment_edit_workout_rest_edit_text.setText(it.toString())
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_edit_workout
    }

}