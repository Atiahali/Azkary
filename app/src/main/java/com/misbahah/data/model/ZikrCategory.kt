package com.misbahah.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zikr_category")
data class ZikrCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "category_name")
    val categoryName: String
)