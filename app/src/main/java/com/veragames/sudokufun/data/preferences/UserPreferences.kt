package com.veragames.sudokufun.data.preferences

data class UserPreferences(
    val theme: AppTheme? = null,
    val darkMode: Boolean = false,
    val followSystemTheme: Boolean = true,
    val soundEffects: Boolean = true,
    val timer: Boolean = true,
    val mistakeLimit: Boolean = true,
    val numberLock: Boolean = true,
    val removeUsedNumbers: Boolean = true,
)
