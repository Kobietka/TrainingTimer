package com.kobietka.trainingtimer.presentaion.common

class TimeToString {

    fun convert(seconds: Int): String {
        val hours = seconds/3600
        val minutes = seconds/60

        if(minutes == 0){
            return if(seconds < 10){
                "0:0$seconds"
            } else {
                "0:$seconds"
            }
        } else if(hours == 0){
            return if(minutes < 10){
                if(seconds%60 < 10){
                    "0$minutes:0${seconds%60}"
                } else {
                    "0$minutes:${seconds%60}"
                }
            } else {
                if(seconds%60 < 10){
                    "$minutes:0${seconds%60}"
                } else {
                    "$minutes:${seconds%60}"
                }
            }
        }

        return "Cannot convert!"
    }

}