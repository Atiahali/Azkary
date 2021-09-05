package org.azkary.data.db.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import org.azkary.data.model.Category
import org.azkary.data.model.Zikr

data class CategoryWithZikr(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "zikr_id",
        associateBy = Junction(CategoryZikrCrossRef::class)
    )
    val azkar: List<Zikr>
)