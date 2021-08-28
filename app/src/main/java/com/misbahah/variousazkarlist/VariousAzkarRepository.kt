package com.misbahah.variousazkarlist

import com.misbahah.data.db.ZikrDao
import com.misbahah.data.model.Zikr
import javax.inject.Inject

class VariousAzkarRepository @Inject constructor(private val zikrDao: ZikrDao) {
    suspend fun getVariousAzkar(): List<Zikr> =
        zikrDao.getVariousAzkar()
}