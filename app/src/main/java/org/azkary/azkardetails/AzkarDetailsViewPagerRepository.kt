package org.azkary.azkardetails

import org.azkary.data.db.CategoryDao
import org.azkary.data.db.ZikrDao
import org.azkary.data.db.relations.CategoryWithZikr
import org.azkary.data.model.Zikr
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