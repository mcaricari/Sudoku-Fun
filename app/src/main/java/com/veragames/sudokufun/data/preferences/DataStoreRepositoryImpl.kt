package com.veragames.sudokufun.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
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
                                preferences[stringPreferencesKey(PreferencesKeys.THEME)]
                                    ?: AppTheme.GREEN.name,
                            ),
                        darkMode =
                            preferences[booleanPreferencesKey(PreferencesKeys.DARK_MODE)] == true,
                    )
                }

        override suspend fun updatePreference(
            key: String,
            value: Any,
        ) {
            context.dataStore.edit { preferences ->
                when (value) {
                    is String -> preferences[stringPreferencesKey(key)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key)] = value
                    else -> Log.e(TAG, "Error updating preference. Wrong types")
                }
            }
        }

        private companion object {
            const val TAG = "DataStoreRepositoryImpl"
        }
    }
