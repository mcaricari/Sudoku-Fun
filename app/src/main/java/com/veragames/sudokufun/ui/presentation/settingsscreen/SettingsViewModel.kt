package com.veragames.sudokufun.ui.presentation.settingsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veragames.sudokufun.domain.usecases.app.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val appUseCases: AppUseCases,
    ) : ViewModel() {
        private val _state = MutableStateFlow(SettingsScreenState())
        val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

        init {
            fetchPreferences()
        }

        private fun fetchPreferences() {
            viewModelScope.launch {
                appUseCases.getPreferences().collect { preferences ->
                    _state.update {
                        it.copy(
                            soundEffects = preferences.soundEffects,
                            chronometer = preferences.timer,
                            mistakeLimit = preferences.mistakeLimit,
                            numberLock = preferences.numberLock,
                            removeUsedNumbers = preferences.removeUsedNumbers,
                        )
                    }
                }
            }
        }
    }
