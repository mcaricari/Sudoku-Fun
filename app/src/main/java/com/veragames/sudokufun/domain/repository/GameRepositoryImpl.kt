package com.veragames.sudokufun.domain.repository

import android.util.Log
import com.veragames.sudokufun.data.board.BoardSupplier
import com.veragames.sudokufun.data.model.Cell
import com.veragames.sudokufun.data.model.Note
import com.veragames.sudokufun.data.model.SudokuValue
import com.veragames.sudokufun.domain.model.BoardSize
import com.veragames.sudokufun.domain.util.Chronometer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GameRepositoryImpl
    @Inject
    constructor(
        private val gameBoardSupplier: BoardSupplier,
    ) : GameRepository {
        private lateinit var boardSize: BoardSize
        private val board: MutableStateFlow<List<Cell>> = MutableStateFlow(emptyList())
        private val solvedBoard: MutableStateFlow<List<Cell>> = MutableStateFlow(emptyList())
        private val userMovements: MutableList<Cell> = mutableListOf()
        private val chronometer = Chronometer()
        private val hintsAvailable = MutableStateFlow(MAX_HINTS)
        private val mistakes = MutableStateFlow(0)

        override suspend fun loadBoard(size: BoardSize) {
            boardSize = size
            board.update {
                gameBoardSupplier.getBoard(size.size).first().map { cell ->
                    if (cell.value == SudokuValue.EMPTY.value) {
                        cell.copy(notes = size.values.map { Note(it) })
                    } else {
                        cell.copy(userCell = false)
                    }
                }
            }
            solvedBoard.update {
                gameBoardSupplier.getSolvedBoard(size.size).first()
            }
        }

        override suspend fun getBoard(): StateFlow<List<Cell>> = board.asStateFlow()

        override suspend fun setCellValue(
            cell: Cell,
            value: Char,
            isHint: Boolean,
        ): Boolean {
            var result = false
            board.update { currentBoard ->
                currentBoard.map { c ->
                    if (c.isSame(cell) && c.completed.not() && c.userCell && cell.value != value) {
                        if (isHint.not()) {
                            userMovements.add(c)
                        }
                        val tmpCell = cell.copy(value = value)
                        val conflicts = checkConflicts(tmpCell)
                        if (conflicts) {
                            mistakes.value += 1
                        }
                        result = conflicts.not()
                        tmpCell.copy(value = value, conflict = conflicts, completed = conflicts.not())
                    } else {
                        c
                    }
                }
            }
            unnoteCellNotes(cell)
            if (result) {
                Log.d(TAG, "Valor actualizado correctamente")
            } else {
                Log.d(TAG, "Conflictos encontrados o celda completada")
            }
            return result
        }

        override suspend fun eraseCellValue(cell: Cell): Boolean {
            var result = false

            board.update { currentBoard ->
                currentBoard.map { c ->
                    if (c.isSame(cell) && c.userCell && c.value != SudokuValue.EMPTY.value) {
                        userMovements.add(c)
                        result = true
                        c.copy(value = SudokuValue.EMPTY.value, conflict = false, completed = false)
                    } else {
                        c
                    }
                }
            }
            unnoteCellNotes(cell)
            if (result) {
                Log.d(TAG, "Valor borrado correctamente")
            } else {
                Log.d(TAG, "No se pudo borrar el valor o la celda no tenia un valor")
            }
            return result
        }

        override suspend fun undoMovement(): Boolean {
            var result = false
            if (userMovements.isNotEmpty()) {
                val lastMovementCell = userMovements.removeLastOrNull()
                if (lastMovementCell != null) {
                    board.update { currentBoard ->
                        currentBoard.map { c ->
                            if (c.isSame(lastMovementCell)) {
                                result = true
                                lastMovementCell.copy(conflict = checkConflicts(lastMovementCell))
                            } else {
                                c
                            }
                        }
                    }
                }
            }
            if (result) {
                Log.d(TAG, "Movimiento deshecho correctamente")
            } else {
                Log.d(TAG, "No se pudo deshacer el movimiento")
            }
            return result
        }

        override suspend fun startChronometer() = chronometer.start()

        override suspend fun pauseChronometer() = chronometer.pause()

        override suspend fun resumeChronometer() = chronometer.resume()

        override suspend fun stopChronometer() = chronometer.stop()

        override suspend fun getChronometer(): StateFlow<Long> = chronometer.getChronometer()

        override suspend fun showHint(): Int {
            if (hintsAvailable.value > 0) {
                hintsAvailable.update { it - 1 }
                solveRandomCell()
            }
            return hintsAvailable.value
        }

        override suspend fun checkGameCompletion(): Boolean {
            board.value.forEach {
                if (it.completed.not() || it.conflict) {
                    return false
                }
            }
            Log.d(TAG, "Juego completado")
            return true
        }

        override suspend fun noteValue(
            cell: Cell,
            value: Char,
        ) {
            if (cell.userCell && cell.completed.not()) {
                board.update { currentBoard ->
                    currentBoard.map { c ->
                        if (c.isSame(cell)) {
                            userMovements.add(c)
                            c.copy(
                                notes =
                                    c.notes.map { note ->
                                        if (note.value.value == value) {
                                            note.copy(noted = note.noted.not())
                                        } else {
                                            note
                                        }
                                    },
                                value = SudokuValue.EMPTY.value,
                            )
                        } else {
                            c
                        }
                    }
                }
            }
        }

        override suspend fun getMistakes(): StateFlow<Int> = mistakes.asStateFlow()

        override suspend fun isRunning(): StateFlow<Boolean> = chronometer.isRunning()

        private fun checkConflicts(cell: Cell): Boolean {
            board.value.forEach { c ->
                if (cell.conflicts(c)) {
                    return true
                }
            }
            return false
        }

        private suspend fun solveRandomCell() {
            val cell = board.value.filter { it.value == SudokuValue.EMPTY.value }.random()
            val possibleValues = boardSize.values.toMutableList()
            possibleValues.shuffle()
            run setValue@{
                possibleValues.forEach {
                    val tmpCell = cell.copy(value = it.value)
                    if (checkConflicts(tmpCell).not()) {
                        setCellValue(cell, it.value, true)
                        return@setValue
                    }
                }
            }
        }

        private fun unnoteCellNotes(cell: Cell) {
            board.update { currentBoard ->
                currentBoard.map { c ->
                    if (c.isSame(cell)) {
                        c.copy(notes = c.notes.map { it.copy(noted = false) })
                    } else {
                        c
                    }
                }
            }
        }

        private companion object {
            private const val TAG = "GameRepositoryImpl"
            private const val MAX_HINTS = 3
        }
    }
