package org.azkary.utilities

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("azkary")

@Singleton //You can ignore this annotation as return `datastore` from `preferencesDataStore` is singletone
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val appDatastore = appContext.dataStore

    suspend fun setCounter(counter: Int, key: String) {
        appDatastore.edit { settings ->
            val preferencesKey = intPreferencesKey(key)
            settings[preferencesKey] = counter
        }
    }

    fun getCounger(key: String): Flow<Int> =
        appDatastore.data
            .map { preferences ->
                val preferencesKey = intPreferencesKey(key)
                preferences[preferencesKey] ?: 0
            }

    private val isFirstRunKey = booleanPreferencesKey("isFirstRun")
    suspend fun setIsFirstRun(isFirstRun: Boolean) {
        appDatastore.edit { settings ->
            settings[isFirstRunKey] = isFirstRun
        }
    }

    suspend fun isFirstRun(): Boolean {
        return appDatastore.data
            .map { preferences ->
                Log.i("TAG", "isfirst: ${preferences[isFirstRunKey] ?: true}")
                preferences[isFirstRunKey] ?: true
            }.first()
    }


}