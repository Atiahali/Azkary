package org.azkary.data.db

import androidx.room.Dao
import androidx.room.Query
import org.azkary.data.db.relations.CategoryWithZikr
import org.azkary.data.model.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>


    @Query("SELECT * FROM category WHERE category_id = :categoryId")
    suspend fun getCategoryAzkarById(categoryId: Int): CategoryWithZikr

}