package com.misbahah.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "zikr")
data class Zikr(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "zikr_id")
    val id: Int,
    val name: String,
    val daleel: String,
    @ColumnInfo(name = "repeating_number")
    val repeatingNumber: Int = 0
): Parcelable
