package org.azkary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.azkary.data.db.relations.CategoryZikrCrossRef
import org.azkary.data.model.Category
import org.azkary.data.model.Zikr

@Database(entities = [Zikr::class, Category::class, CategoryZikrCrossRef::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun zikrDao(): ZikrDao
}