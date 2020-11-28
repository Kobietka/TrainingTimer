package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "completedWorkout")
data class CompletedWorkoutEntity(@PrimaryKey(autoGenerate = true) val id: Int?,
                                  @ColumnInfo val weekId: Int?,
                                  @ColumnInfo val workoutName: String,
                                  @ColumnInfo val workoutCompletionTime: Int,
                                  @ColumnInfo val workoutRepetitionCount: Int,
                                  @ColumnInfo val completionDay: Int,
                                  @ColumnInfo val completionMonth: Int,
                                  @ColumnInfo val completionYear: Int)