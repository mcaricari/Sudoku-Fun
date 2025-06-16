package com.veragames.sudokufun.domain.usecases.app

import com.veragames.sudokufun.data.preferences.DataStoreRepository
import javax.inject.Inject

class UpdateUserPreference
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
    ) {
        suspend operator fun invoke(
            key: String,
            value: Any,
        ) = dataStoreRepository.updatePreference(key, value)
    }
