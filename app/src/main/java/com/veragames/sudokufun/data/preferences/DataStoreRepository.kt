package com.veragames.sudokufun.data.preferences

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getPreferences(): Flow<UserPreferences>

    suspend fun updatePreference(
        key: String,
        value: Any,
    )
}
