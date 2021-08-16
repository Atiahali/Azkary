package com.misbahah.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.misbahah.data.model.Zikr
import com.misbahah.data.model.ZikrCategory

@Database(entities = [Zikr::class, ZikrCategory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun zikrCategoryDao(): ZikrCategoryDao
}