package com.misbahah.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zikr")
data class Zikr(
    @PrimaryKey
    val name: String,
    val zikrCount: Int = -1,
    val daleel: String,
    val saidTimes: Int = 0
)
