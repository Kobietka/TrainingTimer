package com.kobietka.trainingtimer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "week")
data class WeekEntity(@PrimaryKey(autoGenerate = true) val id: Int?,
                      @ColumnInfo val isActive: Boolean,
                      @ColumnInfo val dateRange: String,
                      @ColumnInfo val startDay: Int,
                      @ColumnInfo val startMonth: Int,
                      @ColumnInfo val startYear: Int)