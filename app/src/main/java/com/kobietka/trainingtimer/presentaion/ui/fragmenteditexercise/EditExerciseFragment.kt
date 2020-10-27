package com.kobietka.trainingtimer.presentaion.ui.fragmenteditexercise

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentexercises.ExercisesFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.EditExerciseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import kotlinx.android.synthetic.main.fragment_edit_exercise.*
import javax.inject.Inject


class EditExerciseFragment : BaseFragment() {

    @Inject lateinit var launchEvents: Subject<EventType>
    @Inject lateinit var editExerciseViewModel: EditExerciseViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        compositeDisposable.add(
            launchEvents.subscribe {
                editExerciseViewModel.loadDataById(it.itemId)
                editExerciseViewModel.setId(it.itemId)
            }
        )

        fragment_edit_exercises_back_arrow.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ExercisesFragment())
                .commit()
        }

        fragment_edit_exercises_add.setOnClickListener {
            val id = fragment_edit_exercise_radio_group.checkedRadioButtonId
            val name = fragment_edit_exercise_edit_text_name.text.toString()
            val count = fragment_edit_exercise_count_edit_text.text.toString()

            if (name != "" && count != "" && id != -1) {
                editExerciseViewModel.saveExercise(
                    name,
                    view.findViewById<RadioButton>(id).text.toString(),
                    count.toInt()
                )

                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, ExercisesFragment())
                    .commit()
            }
        }
        editExerciseViewModel.name().observe(viewLifecycleOwner, {
            fragment_edit_exercise_edit_text_name.setText(it)
        })

        editExerciseViewModel.measurementValue().observe(viewLifecycleOwner, {
            fragment_edit_exercise_count_edit_text.setText(it.toString())
        })

        editExerciseViewModel.measurementType().observe(viewLifecycleOwner, {
            when(it!!){
                MeasurementType.Time -> fragment_edit_exercise_radio_button_repetition.isChecked = true
                MeasurementType.Repetition -> fragment_edit_exercise_radio_button_time.isChecked = true
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_edit_exercise
    }
}