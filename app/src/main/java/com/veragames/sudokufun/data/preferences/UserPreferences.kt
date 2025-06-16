package com.veragames.sudokufun.data.preferences

data class UserPreferences(
    val theme: AppTheme? = null,
    val darkMode: Boolean = false,
    val followSystemTheme: Boolean = true,
)
