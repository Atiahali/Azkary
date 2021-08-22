package com.misbahah.daysazkar

import com.misbahah.data.db.CategoryDao
import com.misbahah.data.model.Category
import javax.inject.Inject

class DayAzkarRepository @Inject constructor(private val categoriesDao: CategoryDao) {

    suspend fun getAllCategories(): List<Category> =
        categoriesDao.getAllCategories()

}