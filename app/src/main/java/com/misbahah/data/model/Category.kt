package com.misbahah.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val id: Int,
    @ColumnInfo(name = "category_name")
    val categoryName: String
)