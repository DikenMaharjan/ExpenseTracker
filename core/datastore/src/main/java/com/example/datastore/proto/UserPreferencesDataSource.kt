package com.example.datastore.proto

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "UserPreferencesDataSour"
@Singleton
class UserPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val darkModeKey = booleanPreferencesKey("dark_mode")

    val isDarkModeEnabled: Flow<Boolean> = dataStore.data.map {
        it[darkModeKey] ?: false
    }

    suspend fun toggleTheme() {
        val currentMode = isDarkModeEnabled.first()
        Log.e(TAG, "toggleTheme: $currentMode", )
        dataStore.edit {
            it[darkModeKey] = !currentMode
        }
    }
}