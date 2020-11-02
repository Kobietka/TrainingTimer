package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workoutRelation")
data class WorkoutRelation(@PrimaryKey(autoGenerate = true) val id: Int?,
                           @ColumnInfo val workoutId: Int,
                           @ColumnInfo val exerciseId: Int)