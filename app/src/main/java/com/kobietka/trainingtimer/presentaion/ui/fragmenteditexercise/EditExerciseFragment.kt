package com.kobietka.trainingtimer.presentaion.ui.fragmenteditexercise

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.models.EventType
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentexercises.ExercisesFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.EditExerciseViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import kotlinx.android.synthetic.main.fragment_edit_exercise.*
import javax.inject.Inject


class EditExerciseFragment : BaseFragment() {

    @Inject lateinit var editExerciseViewModel: EditExerciseViewModel
    lateinit var navController: NavController

    var exerciseId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        exerciseId = requireArguments().getString("exerciseId")!!.toInt()
        editExerciseViewModel.loadDataById(exerciseId)
        editExerciseViewModel.setId(exerciseId)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        fragment_edit_exercises_back_arrow.setOnClickListener {
            navController.navigate(R.id.action_editExerciseFragment_to_exercisesFragment)
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

                navController.navigate(R.id.action_editExerciseFragment_to_exercisesFragment)
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