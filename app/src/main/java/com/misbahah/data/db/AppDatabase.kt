package com.misbahah.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.misbahah.data.db.relations.CategoryZikrCrossRef
import com.misbahah.data.model.Category
import com.misbahah.data.model.Zikr

@Database(entities = [Zikr::class, Category::class, CategoryZikrCrossRef::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}