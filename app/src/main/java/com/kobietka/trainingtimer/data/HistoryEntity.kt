package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(@PrimaryKey val id: Int?,
                         @ColumnInfo val workoutId: Int,
                         @ColumnInfo val completeDate: String)