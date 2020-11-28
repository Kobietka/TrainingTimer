package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kobietka.trainingtimer.models.MeasurementType

@Entity(tableName = "completedGoal")
data class CompletedGoal(@PrimaryKey(autoGenerate = true) val id: Int?,
                         @ColumnInfo val name: String,
                         @ColumnInfo val goal: Int,
                         @ColumnInfo val type: MeasurementType,
                         @ColumnInfo val workoutId: Int?,
                         @ColumnInfo val weekId: Int?,
                         @ColumnInfo val creationDate: String,
                         @ColumnInfo val completeDate: String
)