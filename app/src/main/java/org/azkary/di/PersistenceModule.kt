package org.azkary.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.azkary.data.db.AppDatabase
import org.azkary.data.db.CategoryDao
import org.azkary.data.db.ZikrDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase =
        Room
            .databaseBuilder(application, AppDatabase::class.java, "zikr.db")
            .fallbackToDestructiveMigration()
            .createFromAsset("database/zikr.db")
            .build()

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao =
        appDatabase.categoryDao()

    @Provides
    @Singleton
    fun provideZikrDao(appDatabase: AppDatabase): ZikrDao =
        appDatabase.zikrDao()
}