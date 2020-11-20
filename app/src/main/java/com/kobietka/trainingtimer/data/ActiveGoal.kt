package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kobietka.trainingtimer.models.MeasurementType

@Entity(tableName = "activeGoal")
data class ActiveGoal(@PrimaryKey(autoGenerate = true) val id: Int?,
                      @ColumnInfo val name: String,
                      @ColumnInfo val type: MeasurementType,
                      @ColumnInfo val goal: Int,
                      @ColumnInfo val currentProgress: Int,
                      @ColumnInfo val isAttached: Boolean,
                      @ColumnInfo val workoutId: Int?,
                      @ColumnInfo val creationDate: String)