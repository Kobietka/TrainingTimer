package com.kobietka.trainingtimer.models

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toType(value: String) = enumValueOf<MeasurementType>(value)

    @TypeConverter
    fun toType(value: MeasurementType) = value.name

    @TypeConverter
    fun fromListToString(ids: MutableList<Int>): String = ids.joinToString(";")

    @TypeConverter
    fun fromStringToList(idsString: String): MutableList<Int> = idsString.split(";")
        .map { it.toInt() }.toMutableList()

}