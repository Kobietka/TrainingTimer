package com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentexercises.ExercisesFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.AddExerciseViewModel
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject


class ExerciseAddFragment : BaseFragment() {

    @Inject lateinit var addExerciseViewModel: AddExerciseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presentationComponent.inject(this)

        fragment_exercises_add.setOnClickListener {
            val id = fragment_create_exercise_radio_group.checkedRadioButtonId
            val name = fragment_create_exercise_edit_text_name.text.toString()
            val count = fragment_create_exercise_count_edit_text.text.toString()

            if(name != "" && count != "" && id != -1){
                addExerciseViewModel.createExercise(
                    name,
                    view.findViewById<RadioButton>(id).text.toString(),
                    count.toInt()
                )

                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, ExercisesFragment())
                    .commit()

            } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_add_exercise
    }

}