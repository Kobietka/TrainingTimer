package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kobietka.trainingtimer.models.MeasurementType


@Entity(tableName = "exercises")
data class ExerciseEntity(@PrimaryKey(autoGenerate = true) val id: Int?,
                          @ColumnInfo val name: String,
                          @ColumnInfo val workoutId: Int?,
                          @ColumnInfo val measurementType: MeasurementType,
                          @ColumnInfo val measurementValue: Int,
                          @ColumnInfo val creationDate: String,
                          @ColumnInfo val totalMeasurementValue: Int)