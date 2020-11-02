package com.kobietka.trainingtimer.models

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toType(value: String) = enumValueOf<MeasurementType>(value)

    @TypeConverter
    fun toType(value: MeasurementType) = value.name

    @TypeConverter
    fun fromListToString(exercises: MutableList<Int>): String {
        return if(exercises.isEmpty()) "null"
        else exercises.joinToString(";")
    }

    @TypeConverter
    fun fromStringToList(exercises: String): MutableList<Int> {
        return if(exercises == "null") arrayListOf()
        else {
            exercises.split(";")
                .map { it.toInt() }.toMutableList()
        }
    }

}