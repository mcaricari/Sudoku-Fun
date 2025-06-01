package com.veragames.sudokufun.data.preferences

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getPreferences(): Flow<UserPreferences>

    suspend fun updateStringPreference(
        key: Preferences.Key<String>,
        value: String,
    )
}
