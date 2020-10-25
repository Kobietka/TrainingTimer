package com.kobietka.trainingtimer

import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    FragmentMainTest::class,
    WorkoutRepositoryTest::class,
    ExerciseRepositoryTest::class
)
class TestSuite