package com.veragames.sudokufun.ui.presentation.settingsscreen

data class SettingsScreenState(
    val soundEffects: Boolean = true,
    val chronometer: Boolean = true,
    val mistakeLimit: Boolean = true,
    val numberLock: Boolean = true,
    val removeUsedNumbers: Boolean = true,
    val darkMode: Boolean = false,
    val systemTheme: Boolean = true,
)
