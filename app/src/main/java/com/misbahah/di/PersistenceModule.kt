package com.misbahah.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.misbahah.data.db.AppDatabase
import com.misbahah.data.db.CategoryDao
import com.misbahah.data.db.ZikrDao
import com.misbahah.utilities.MAIN_ACTIVITY_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        context.getSharedPreferences(MAIN_ACTIVITY_PREFS, Context.MODE_PRIVATE)

}