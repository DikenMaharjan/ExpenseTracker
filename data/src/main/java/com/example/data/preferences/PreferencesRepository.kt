package com.example.data.preferences

import com.example.datastore.proto.UserPreferencesDataSource
import com.example.utils.di.ApplicationScope
import com.example.utils.dispatcher.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
    @ApplicationScope private val scope: CoroutineScope,
    private val appDispatchers: AppDispatchers
) {

    val isDarkModeEnabled = userPreferencesDataSource
        .isDarkModeEnabled.stateIn(
            scope,
            SharingStarted.Lazily,
            runBlocking { userPreferencesDataSource.isDarkModeEnabled.first() }
        )

    suspend fun toggleTheme() {
        withContext(appDispatchers.background) {
            userPreferencesDataSource.toggleTheme()
        }
    }
}