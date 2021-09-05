package org.azkary.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.azkary.data.db.AppDatabase
import org.azkary.data.db.CategoryDao
import org.azkary.data.db.ZikrDao
import org.azkary.utilities.APPLICATION_PREFS
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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(APPLICATION_PREFS, Context.MODE_PRIVATE)
}