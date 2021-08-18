package com.misbahah.data.model

import androidx.room.*

@Entity(tableName = "zikr")
data class Zikr(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "zikr_id")
    val id: Int,
    val name: String,
    @ColumnInfo(name = "zikr_count")
    val zikrCount: Int = -1,
    val daleel: String,
    @ColumnInfo(name = "said_times")
    val saidTimes: Int = 0
)
