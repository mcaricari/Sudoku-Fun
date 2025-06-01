package com.veragames.sudokufun.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("user_preferences")

class DataStoreRepositoryImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : DataStoreRepository {
        override fun getPreferences(): Flow<UserPreferences> =
            context.dataStore.data
                .catch { exception ->
                    Log.e(TAG, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                }.map { preferences ->
                    UserPreferences(
                        theme =
                            AppTheme.valueOf(
                                preferences[PreferencesKeys.THEME] ?: AppTheme.GREEN.name,
                            ),
                    )
                }

        override suspend fun updateStringPreference(
            key: Preferences.Key<String>,
            value: String,
        ) {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }

        private companion object {
            const val TAG = "DataStoreRepositoryImpl"
        }
    }
