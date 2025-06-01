package com.veragames.sudokufun.domain.usecases.app

import androidx.datastore.preferences.core.Preferences
import com.veragames.sudokufun.data.preferences.DataStoreRepository
import javax.inject.Inject

class UpdateStringUserPreference
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
    ) {
        suspend operator fun invoke(
            key: Preferences.Key<String>,
            value: String,
        ) = dataStoreRepository.updateStringPreference(key, value)
    }
