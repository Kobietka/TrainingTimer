package com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseFragment
import com.kobietka.trainingtimer.presentaion.viewmodels.AddExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseAddFragment : BaseFragment() {

    @Inject lateinit var addExerciseViewModel: AddExerciseViewModel
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        fragment_create_exercises_back_arrow.setOnClickListener {
            navController.navigate(R.id.action_exerciseAddFragment_to_exercisesFragment)
        }

        fragment_create_exercises_add.setOnClickListener {
            val id = fragment_create_exercise_radio_group.checkedRadioButtonId
            val name = fragment_create_exercise_edit_text_name.text.toString()
            val count = fragment_create_exercise_count_edit_text.text.toString()

            if(name != "" && count != "" && id != -1){
                addExerciseViewModel.createExercise(
                    name,
                    view.findViewById<RadioButton>(id).text.toString(),
                    count.toInt()
                )

                navController.navigate(R.id.action_exerciseAddFragment_to_exercisesFragment)

            } else Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG).show()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_add_exercise
    }

}