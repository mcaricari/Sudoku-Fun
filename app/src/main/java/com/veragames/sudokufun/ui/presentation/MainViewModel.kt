package com.veragames.sudokufun.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veragames.sudokufun.data.preferences.UserPreferences
import com.veragames.sudokufun.domain.usecases.app.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val appUseCases: AppUseCases,
    ) : ViewModel() {
        val preferencesState: StateFlow<UserPreferences> =
            appUseCases.getPreferences().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UserPreferences(),
            )
    }
