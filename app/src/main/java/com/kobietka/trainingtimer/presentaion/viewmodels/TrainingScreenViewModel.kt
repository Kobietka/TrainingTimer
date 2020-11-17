package com.kobietka.trainingtimer.presentaion.viewmodels

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.data.ExerciseEntity
import com.kobietka.trainingtimer.data.HistoryEntity
import com.kobietka.trainingtimer.data.WorkoutEntity
import com.kobietka.trainingtimer.models.MeasurementType
import com.kobietka.trainingtimer.presentaion.common.TimeToString
import com.kobietka.trainingtimer.presentaion.common.Timer
import com.kobietka.trainingtimer.repositories.ExerciseRepository
import com.kobietka.trainingtimer.repositories.HistoryRepository
import com.kobietka.trainingtimer.repositories.WorkoutRelationRepository
import com.kobietka.trainingtimer.repositories.WorkoutRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates


class TrainingScreenViewModel
@Inject constructor(private val relationRepository: WorkoutRelationRepository,
                    private val exercisesRepository: ExerciseRepository,
                    private val workoutRepository: WorkoutRepository,
                    private val historyRepository: HistoryRepository,
                    @ApplicationContext val appContext: Context){


    lateinit var onTrainingEndFunction: (time: String, workoutId: String) -> Unit
    lateinit var onWorkoutHaveZeroExercises: () -> Unit

    lateinit var nextMeasurementType: MeasurementType
    lateinit var nextName: String
    var exerciseNumber = 0
    var nextMeasurementValue by Delegates.notNull<Int>()
    var lastWasBreak = true
    var firstRun = true
    var overallTime = ""

    private val timer = Timer()
    private val overallTimer = Timer()
    private val converter = TimeToString()

    private var exercisesIdsList = listOf<Int>()
    private var exercises = mutableListOf<ExerciseEntity>()
    private var currentExerciseNumber = 1
    private lateinit var currentExercise: ExerciseEntity
    private lateinit var currentWorkout: WorkoutEntity

    val compositeDisposable = CompositeDisposable()
    private val workoutIds = BehaviorSubject.create<Int>().toSerialized()
    private val exercisesIds = BehaviorSubject.create<Int>().toSerialized()

    private val _exerciseName = MutableLiveData<String>()
    private val _exerciseNumber = MutableLiveData<String>()
    private val _timeOrRep = MutableLiveData<String>()
    private val _playOrPause = MutableLiveData<Boolean>()
    private val _fabGone = MutableLiveData<Boolean>()
    private val _nextExercise = MutableLiveData<String>()

    init {

        compositeDisposable.add(
            workoutIds.subscribe {
                loadExercises(it)
                loadWorkout(it)
            }
        )

        compositeDisposable.add(
            exercisesIds.subscribe {
                loadExercise(it)
            }
        )

        timer.onTick {
            _timeOrRep.value = converter.convert(it)
        }

        timer.onFinish {
            if(firstRun){
                val mp = MediaPlayer.create(appContext, R.raw.trainingtimersound)
                mp.start()
                switchExerciseId(exercisesIdsList[0])
                _nextExercise.value = ""
                _fabGone.value = false
                firstRun = false
            } else {
                if(!lastWasBreak){
                    _exerciseName.value = "Break"
                    _exerciseNumber.value = "It's time to rest!"
                    _timeOrRep.value = converter.convert(currentWorkout.restTime)
                    val mp = MediaPlayer.create(appContext, R.raw.trainingtimersound)
                    mp.start()
                    if(currentExerciseNumber < exercisesIdsList.size){
                        loadNextExerciseName(exercisesIdsList[currentExerciseNumber])
                    }
                    CoroutineScope(IO).launch {
                        timer.start(currentWorkout.restTime)
                    }
                    lastWasBreak = true
                } else {
                    if(currentExerciseNumber == exercisesIdsList.size){
                        val mp = MediaPlayer.create(appContext, R.raw.trainingtimersound)
                        mp.start()
                        overallTimer.stopCountUp()
                        historyRepository.insert(HistoryEntity(null, currentWorkout.name, currentWorkout.id!!, getCurrentDate()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                        onTrainingEndFunction.invoke(overallTime, currentWorkout.id.toString())
                        clearComposite()
                    } else {
                        _nextExercise.value = ""
                        switchExerciseId(exercisesIdsList[currentExerciseNumber])
                        currentExerciseNumber++
                        val mp = MediaPlayer.create(appContext, R.raw.trainingtimersound)
                        mp.start()
                    }
                }
            }
        }

        overallTimer.setOnCountUpStop {
            overallTime = converter.convert(it)
        }

        timer.onStart {
            println()
        }

        timer.onPause {
            _playOrPause.value = true
        }

        timer.onResume {
            _playOrPause.value = false
        }

        timer.onStart {
            _playOrPause.value = false
        }
    }

    fun onFabClick(){
        if(nextMeasurementType == MeasurementType.Time){
            if(!timer.isRunning){
                if(timer.wasRunning){
                    CoroutineScope(IO).launch {
                        timer.resume()
                    }
                }
            } else timer.pause()
        } else {
            if(currentExerciseNumber == exercisesIdsList.size){
                val mp = MediaPlayer.create(appContext, R.raw.trainingtimersound)
                mp.start()
                overallTimer.stopCountUp()
                historyRepository.insert(HistoryEntity(null, currentWorkout.name, currentWorkout.id!!, getCurrentDate()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                onTrainingEndFunction.invoke(overallTime, currentWorkout.id.toString())
                clearComposite()
            } else {
                if(!lastWasBreak){
                    _exerciseName.value = "Break"
                    _exerciseNumber.value = "It's time to rest!"
                    _timeOrRep.value = converter.convert(currentWorkout.restTime)
                    val mp = MediaPlayer.create(appContext, R.raw.trainingtimersound)
                    mp.start()
                    if(currentExerciseNumber <= exercisesIdsList.size){
                        loadNextExerciseName(exercisesIdsList[currentExerciseNumber])
                    }
                    CoroutineScope(IO).launch {
                        timer.start(currentWorkout.restTime)
                    }
                    lastWasBreak = true
                } else {
                    if(!timer.isRunning){
                        if(timer.wasRunning){
                            CoroutineScope(IO).launch {
                                timer.resume()
                            }
                        }
                    } else timer.pause()
                    if(!timer.isRunning && !timer.isPaused){
                        _nextExercise.value = ""
                        switchExerciseId(exercisesIdsList[currentExerciseNumber])
                        currentExerciseNumber++
                        val mp = MediaPlayer.create(appContext, R.raw.trainingtimersound)
                        mp.start()
                    }
                }
            }
        }
    }

    fun fabGone(): LiveData<Boolean> {
        return _fabGone
    }

    fun nextExercise(): LiveData<String> {
        return _nextExercise
    }

    fun exerciseName(): LiveData<String> {
        return _exerciseName
    }

    fun exerciseNumber(): LiveData<String> {
        return _exerciseNumber
    }

    fun timeOrRep(): LiveData<String> {
        return _timeOrRep
    }

    fun playOrPause(): LiveData<Boolean> {
        return _playOrPause
    }

    fun switchWorkoutId(id: Int){
        workoutIds.onNext(id)
    }

    private fun switchExerciseId(id: Int){
        exercisesIds.onNext(id)
    }

    private fun bind(){
        exerciseNumber++
        _exerciseName.value = nextName
        _exerciseNumber.value = "Exercise $exerciseNumber"
        run()
    }

    private fun run(){
        if(nextMeasurementType == MeasurementType.Time){
            _timeOrRep.value = converter.convert(nextMeasurementValue)
            CoroutineScope(Main).launch{
                timer.start(nextMeasurementValue)
            }
            lastWasBreak = false
        } else {
            _timeOrRep.value = "x$nextMeasurementValue"
            _playOrPause.value = true
            lastWasBreak = false
        }
    }

    fun onTrainingEnd(function: (time: String, workoutId: String) -> Unit){
        onTrainingEndFunction = function
    }

    fun onWorkoutZeroExercises(function: () -> Unit){
        onWorkoutHaveZeroExercises = function
    }

    fun clearComposite(){
        compositeDisposable.clear()
    }

    private fun loadExercise(id: Int){
        compositeDisposable.add(
            exercisesRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    nextMeasurementType = it.measurementType
                    nextName = it.name
                    nextMeasurementValue = it.measurementValue
                    bind()
                }
        )
    }

    private fun loadWorkout(id: Int){
        compositeDisposable.add(
            workoutRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currentWorkout = it
                }
        )
    }

    private fun loadNextExerciseName(id: Int) {
        compositeDisposable.add(
            exercisesRepository.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    _nextExercise.value = "Next: ${it.name}"
                }
        )
    }

    private fun loadExercises(workoutId: Int){
        compositeDisposable.add(
            relationRepository.getExercisesByWorkoutId(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it.isEmpty()) onWorkoutHaveZeroExercises.invoke()
                    else {
                        exercisesIdsList = it
                        _exerciseName.value = "Get Ready"
                        _exerciseNumber.value = "Training will start soon"
                        _timeOrRep.value = converter.convert(5)
                        loadNextExerciseName(exercisesIdsList[0])
                        CoroutineScope(IO).launch {
                            timer.start(5)
                        }
                        _fabGone.value = true
                        CoroutineScope(IO).launch {
                            overallTimer.countUp()
                        }
                    }
                }
        )
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                calendar.get(Calendar.MONTH).toString() + "/" +
                calendar.get(Calendar.YEAR).toString()
    }

}