package com.misbahah.data.db

import androidx.room.Dao
import androidx.room.Query
import com.misbahah.data.db.relations.CategoryWithZikr
import com.misbahah.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>


    @Query("SELECT * FROM category WHERE category_id = :categoryId")
    suspend fun getCategoryAzkarById(categoryId: Int): CategoryWithZikr

}