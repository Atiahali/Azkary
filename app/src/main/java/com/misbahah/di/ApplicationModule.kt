package com.misbahah.di

import android.content.Context
import android.content.SharedPreferences
import com.misbahah.utilities.MAIN_ACTIVITY_PREFS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//interface ApplicationModule {
//
//
//}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(MAIN_ACTIVITY_PREFS, Context.MODE_PRIVATE)
}