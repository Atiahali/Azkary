package org.azkary.data.db

import androidx.room.Dao
import androidx.room.Query
import org.azkary.data.model.Zikr

@Dao
interface ZikrDao {

    @Query("SELECT * FROM zikr WHERE is_in_category = 0")
    suspend fun getVariousAzkar(): List<Zikr>

}