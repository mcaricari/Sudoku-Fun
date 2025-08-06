package com.veragames.sudokufun.ui.presentation.gamescreen

import androidx.compose.ui.unit.IntOffset
import com.veragames.sudokufun.data.model.SudokuValue
import com.veragames.sudokufun.data.preferences.AppTheme
import com.veragames.sudokufun.ui.model.CellUI

data class GameScreenState(
    val board: List<CellUI> = emptyList(),
    val time: String = "00:00",
    val difficulty: String = "Easy",
    val score: Int = 0,
    val mistakes: Int = 0,
    val maxMistakes: Int = 3,
    val userValues: List<SudokuValue> = emptyList(),
    val gameRunning: Boolean = true,
    val hintEnabled: Boolean = true,
    val hintsRemaining: Int = 3,
    val completed: Boolean = false,
    val notesEnabled: Boolean = false,
    val selectedValue: SudokuValue? = null,
    val showThemeSelector: Boolean = false,
    val currentTheme: AppTheme = AppTheme.GREEN,
    val darkModeEnabled: Boolean = false,
    val systemDefaultThemeEnabled: Boolean = true,
    val themeIconOffset: IntOffset = IntOffset(0, 0),
    val numberLockEnabled: Boolean = true,
    val showChronometer: Boolean = true,
    val mistakeLimitEnabled: Boolean = true,
)
