package com.misbahah.data.db

import androidx.room.Dao
import androidx.room.Query
import com.misbahah.data.model.Zikr

@Dao
interface ZikrDao {

    @Query("SELECT * FROM zikr WHERE is_in_category = 0")
    suspend fun getVariousAzkar(): List<Zikr>

}