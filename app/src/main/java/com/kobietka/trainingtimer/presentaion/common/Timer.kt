package com.kobietka.trainingtimer.presentaion.common

import android.util.Log
import kotlinx.coroutines.*


class Timer {

    private lateinit var onTick: (time: Int) -> Unit
    private lateinit var onFinish: (isRest: Boolean) -> Unit
    private lateinit var onPause: () -> Unit
    private lateinit var onResume: () -> Unit
    private lateinit var onStart: () -> Unit
    private lateinit var onCountUpStop: (time: Int) -> Unit

    var isPaused = false
    var wasRunning = false
    var isRest = false
    var isRunning = false
    var timeLeftThisCycle = 0
    var timeLeft = 0
    var isCountDownTimerRunning = true
    var countUpTime = 0

    suspend fun countUp(){
        while(isCountDownTimerRunning){
            countUpTime++
            delay(1000)
        }
    }

    suspend fun start(seconds: Int){
        withContext(Dispatchers.Main){
            onStart.invoke()
        }
        isRunning = true
        wasRunning = true
        timeLeft = seconds
        timeLeftThisCycle = seconds

        while (timeLeft > 0){
            if(isRunning){
                timeLeft -= 1
                delay(1000)
                withContext(Dispatchers.Main){
                    onTick.invoke(timeLeft)
                }
            } else break
        }
        if(isRunning){
            withContext(Dispatchers.Main){
                onFinish.invoke(isRest)
            }
        }
    }

    fun stopCountUp(){
        onCountUpStop.invoke(countUpTime)
        isCountDownTimerRunning = false
    }

    fun pause(){
        isRunning = false
        isPaused = true
        onPause.invoke()
    }

    suspend fun resume(){
        isPaused = false
        withContext(Dispatchers.Main){
            onResume.invoke()
        }
        start(timeLeft)
    }

    fun onStart(function: () -> Unit) {
        onStart = function
    }

    fun onTick(function: (time: Int) -> Unit){
        onTick = function
    }

    fun onFinish(function: (isRest: Boolean) -> Unit) {
        onFinish = function
    }

    fun onPause(function: () -> Unit){
        onPause = function
    }

    fun onResume(function: () -> Unit){
        onResume = function
    }

    fun setOnCountUpStop(function: (time: Int) -> Unit) {
        onCountUpStop = function
    }

}