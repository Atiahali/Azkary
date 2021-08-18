package com.misbahah.data.db.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "category_zikr_cross_ref",
    primaryKeys = ["category_id", "zikr_id"])
data class CategoryZikrCrossRef(
    @ColumnInfo(name = "category_id") val categoryId : Long,
    @ColumnInfo(name = "zikr_id") val zikrId: Long
)