package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(@PrimaryKey(autoGenerate = true) val id: Int?,
                         @ColumnInfo val name: String,
                         @ColumnInfo val restTime: Int,
                         @ColumnInfo val exercises: MutableList<Int>,
                         @ColumnInfo val creationDate: String,
                         @ColumnInfo val totalRepetitionCount: Int,
                         @ColumnInfo val totalTimeCount: Int)