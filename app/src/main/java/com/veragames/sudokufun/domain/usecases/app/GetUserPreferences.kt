package com.veragames.sudokufun.domain.usecases.app

import com.veragames.sudokufun.data.preferences.DataStoreRepository
import javax.inject.Inject

class GetUserPreferences
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
    ) {
        operator fun invoke() = dataStoreRepository.getPreferences()
    }
