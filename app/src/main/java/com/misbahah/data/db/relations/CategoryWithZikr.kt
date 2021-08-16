package com.misbahah.data.db.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.misbahah.data.model.Category
import com.misbahah.data.model.Zikr

data class CategoryWithZikr(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "zikr_id",
        associateBy = Junction(CategoryZikrCrossRef::class)
    )
    val zikr: List<Zikr>
)