package org.azkary.daysazkar

import org.azkary.data.db.CategoryDao
import org.azkary.data.model.Category
import javax.inject.Inject

class DayAzkarRepository @Inject constructor(private val categoriesDao: CategoryDao) {

    suspend fun getAllCategories(): List<Category> =
        categoriesDao.getAllCategories()

}