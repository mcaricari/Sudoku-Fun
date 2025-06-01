package com.veragames.sudokufun.ui.presentation.gamescreen

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veragames.sudokufun.data.model.SudokuValue
import com.veragames.sudokufun.data.preferences.AppTheme
import com.veragames.sudokufun.data.preferences.PreferencesKeys
import com.veragames.sudokufun.domain.model.BoardSize
import com.veragames.sudokufun.domain.model.CellStatus
import com.veragames.sudokufun.domain.usecases.app.AppUseCases
import com.veragames.sudokufun.domain.usecases.game.GameUseCases
import com.veragames.sudokufun.ui.Dimens
import com.veragames.sudokufun.ui.model.CellUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameViewModel
    @Inject
    constructor(
        private val gameUseCases: GameUseCases,
        private val appUseCases: AppUseCases,
    ) : ViewModel() {
        private val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        private val initiated = MutableStateFlow(false)
        private val _state = MutableStateFlow(GameState())
        val state: StateFlow<GameState> = _state.asStateFlow()

        init {
            getBoard()
            startResolutionChronometer()
            checkIfGameIsRunning()
            viewModelScope.launch {
                updateTheme()
            }
        }

        fun selectCell(cellUI: CellUI) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        board =
                            it.board.map { cUI ->
                                when {
                                    cellUI.cell.isSame(cUI.cell) -> {
                                        cUI.copy(status = CellStatus.SELECTED)
                                    }

                                    cUI.status == CellStatus.SELECTED -> cUI.copy(status = CellStatus.NORMAL)
                                    else -> cUI
                                }
                            },
                    )
                }
                updateStatus()
            }
        }

        fun setCellValue(value: SudokuValue) {
            viewModelScope.launch {
                gameUseCases.setCellValue(selectedCell().cell, value)
                if (gameUseCases.checkGameCompletion()) {
                    gameUseCases.stopChronometer()
                    _state.update { it.copy(completed = true) }
                }
            }
        }

        fun eraseCellValue() {
            viewModelScope.launch {
                gameUseCases.eraseCellValue(selectedCell().cell)
            }
        }

        fun undoMovement() {
            viewModelScope.launch {
                gameUseCases.undoMovement()
            }
        }

        fun pauseGame() {
            viewModelScope.launch {
                gameUseCases.pauseChronometer()
            }
        }

        fun resumeGame() {
            viewModelScope.launch {
                gameUseCases.resumeChronometer()
            }
        }

        fun showHint() {
            viewModelScope.launch {
                val hintsRemaining = gameUseCases.showHint()
                val hintEnabled = hintsRemaining > 0
                _state.update {
                    it.copy(
                        hintEnabled = hintEnabled,
                        hintsRemaining = hintsRemaining,
                    )
                }
            }
        }

        fun enableNotes(value: Boolean) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        notesEnabled = value,
                    )
                }
            }
        }

        fun noteValue(sudokuValue: SudokuValue) {
            viewModelScope.launch {
                gameUseCases.noteValue(selectedCell().cell, sudokuValue)
            }
        }

        fun selectValue(sudokuValue: SudokuValue?) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        selectedValue = sudokuValue,
                    )
                }
            }
        }

        fun showThemeSelector(show: Boolean) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        showThemeSelector = show,
                    )
                }
            }
        }

        fun changeTheme(theme: AppTheme) {
            viewModelScope.launch {
                appUseCases.updateStringUserPreference(PreferencesKeys.THEME, theme.name)
                updateTheme()
            }
        }

        fun setIconThemePosition(offset: Offset) {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        themeIconOffset =
                            IntOffset(
                                offset.x.toInt().plus(Dimens.THEME_SELECTOR_TO_THEME_ICON_OFFSET_X),
                                offset.y.toInt().plus(Dimens.THEME_SELECTOR_TO_THEME_ICON_OFFSET_Y),
                            ),
                    )
                }
            }
        }

        private suspend fun updateTheme() {
            appUseCases.getPreferences().collect { preferences ->
                _state.update {
                    it.copy(
                        currentTheme = preferences.theme!!,
                    )
                }
            }
        }

        private fun updateStatus() {
            _state.update {
                it.copy(
                    board =
                        it.board.map { cUI ->
                            cUI.copy(
                                status =
                                    when {
                                        cUI.status == CellStatus.SELECTED -> CellStatus.SELECTED
                                        selectedCell().cell.conflicts(cUI.cell) -> CellStatus.CONFLICT

                                        selectedCell().cell.implicates(cUI.cell) -> CellStatus.IMPLICATED
                                        selectedCell().cell.value == cUI.cell.value &&
                                            selectedCell().cell.value != SudokuValue.EMPTY.value -> {
                                            CellStatus.COMMON_NUMBER
                                        }

                                        else -> CellStatus.NORMAL
                                    },
                            )
                        },
                )
            }
        }

        private fun getBoard() {
            viewModelScope.launch {
                gameUseCases.loadBoard(BoardSize.NINE)
                gameUseCases.getBoard().collect { board ->
                    if (initiated.value.not()) {
                        _state.update {
                            it.copy(
                                board =
                                    board.map { cell ->
                                        CellUI(cell)
                                    },
                            )
                        }
                        selectCell(_state.value.board.find { cellUI -> cellUI.cell.value == SudokuValue.EMPTY.value }!!)
                        initiated.value = true
                    } else {
                        _state.update {
                            it.copy(
                                board =
                                    it.board.mapIndexed { index, cUI ->
                                        cUI.copy(cell = board[index])
                                    },
                            )
                        }
                        updateStatus()
                    }
                }
            }
        }

        private fun startResolutionChronometer() {
            viewModelScope.launch {
                gameUseCases.startChronometer()
                gameUseCases.getChronometer().collect {
                    _state.update { state ->
                        state.copy(
                            time = formatter.format(it),
                        )
                    }
                }
            }
        }

        private fun checkIfGameIsRunning() {
            viewModelScope.launch {
                gameUseCases.checkIfGameIsRunning().collect {
                    _state.update { state ->
                        state.copy(
                            gameRunning = it,
                        )
                    }
                }
            }
        }

        private fun selectedCell(): CellUI = _state.value.board.find { it.status == CellStatus.SELECTED }!!
    }
