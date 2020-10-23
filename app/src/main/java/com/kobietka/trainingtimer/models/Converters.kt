package com.kobietka.trainingtimer.models

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toType(value: String) = enumValueOf<MeasurementType>(value)

    @TypeConverter
    fun toType(value: MeasurementType) = value.name

}