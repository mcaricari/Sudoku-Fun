package com.veragames.sudokufun.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veragames.sudokufun.data.preferences.AppTheme
import com.veragames.sudokufun.data.preferences.UserPreferences
import com.veragames.sudokufun.domain.usecases.app.AppUseCases
import com.veragames.sudokufun.ui.model.AppThemeUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserPreferencesUI(
    val darkMode: Boolean = false,
    val followSystemTheme: Boolean = false,
    val appTheme: AppThemeUI? = null,
)

data class AppState(
    val preferences: UserPreferencesUI = UserPreferencesUI(),
)

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val appUseCases: AppUseCases,
    ) : ViewModel() {
        private val _state = MutableStateFlow(AppState())
        val state: StateFlow<AppState> = _state.asStateFlow()

        init {
            observePreferences()
        }

        private fun observePreferences() {
            viewModelScope.launch {
                appUseCases.getPreferences().collect { newPrefs ->
                    _state.update { it.copy(preferences = newPrefs.toUIPref()) }
                }
            }
        }
    }

private fun UserPreferences.toUIPref(): UserPreferencesUI =
    UserPreferencesUI(
        darkMode = darkMode,
        followSystemTheme = followSystemTheme,
        appTheme =
            when (theme) {
                AppTheme.GREEN -> AppThemeUI.GREEN
                AppTheme.RED -> AppThemeUI.RED
                AppTheme.BLUE -> AppThemeUI.BLUE
                AppTheme.GREY -> AppThemeUI.GREY
                null -> AppThemeUI.GREEN
            },
    )
