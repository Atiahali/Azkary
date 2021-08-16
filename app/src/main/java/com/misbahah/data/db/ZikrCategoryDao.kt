package com.misbahah.data.db

import androidx.room.Dao
import androidx.room.Query
import com.misbahah.data.model.ZikrCategory

@Dao
interface ZikrCategoryDao {

    @Query("SELECT * FROM zikr_category")
    suspend fun getAllCategories(): List<ZikrCategory>

}