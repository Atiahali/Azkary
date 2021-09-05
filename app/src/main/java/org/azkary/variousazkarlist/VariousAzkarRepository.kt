package org.azkary.variousazkarlist

import org.azkary.data.db.ZikrDao
import org.azkary.data.model.Zikr
import javax.inject.Inject

class VariousAzkarRepository @Inject constructor(private val zikrDao: ZikrDao) {
    suspend fun getVariousAzkar(): List<Zikr> =
        zikrDao.getVariousAzkar()
}