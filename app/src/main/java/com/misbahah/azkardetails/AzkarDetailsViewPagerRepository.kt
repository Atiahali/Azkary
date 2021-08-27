package com.misbahah.azkardetails

import com.misbahah.data.db.CategoryDao
import com.misbahah.data.db.ZikrDao
import com.misbahah.data.db.relations.CategoryWithZikr
import com.misbahah.data.model.Zikr
import javax.inject.Inject

class AzkarDetailsViewPagerRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val zikrDao: ZikrDao
) {
    suspend fun getAzkarByCategoryId(categoryId: Int): CategoryWithZikr =
        categoryDao.getCategoryAzkarById(categoryId)

    suspend fun getVariousAzkar(): List<Zikr> =
        zikrDao.getVariousAzkar()
}