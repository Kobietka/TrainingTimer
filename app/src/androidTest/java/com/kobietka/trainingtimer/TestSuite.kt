package com.kobietka.trainingtimer

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    FragmentAddExerciseTest::class,
    FragmentMainTest::class,
    WorkoutRepositoryTest::class,
    ExerciseRepositoryTest::class,
    FragmentExercisesTest::class,
    FragmentEditExerciseTest::class,
    FragmentWorkoutsTest::class,
    FragmentCreateWorkoutTest::class
)
class TestSuite