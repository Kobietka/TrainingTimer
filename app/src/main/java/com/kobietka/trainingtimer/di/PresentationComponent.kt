package com.kobietka.trainingtimer.di

import com.kobietka.trainingtimer.presentaion.ui.MainActivity
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddexercise.ExerciseAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentaddworkout.WorkoutAddFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentchooseexercise.ChooseExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenteditexercise.EditExerciseFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenteditworkout.EditWorkoutFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentexercises.ExercisesFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenthistory.HistoryFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentmainmenu.MainFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenttrain.TrainFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmenttrainingscreen.TrainingScreenFragment
import com.kobietka.trainingtimer.presentaion.ui.fragmentworkouts.WorkoutsFragment
import dagger.Subcomponent


@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(exerciseAddFragment: ExerciseAddFragment)
    fun inject(exercisesFragment: ExercisesFragment)
    fun inject(editExerciseFragment: EditExerciseFragment)
    fun inject(workoutsFragment: WorkoutsFragment)
    fun inject(workoutAddFragment: WorkoutAddFragment)
    fun inject(editWorkoutFragment: EditWorkoutFragment)
    fun inject(chooseExerciseFragment: ChooseExerciseFragment)
    fun inject(trainFragment: TrainFragment)
    fun inject(trainingScreenFragment: TrainingScreenFragment)
    fun inject(historyFragment: HistoryFragment)
}