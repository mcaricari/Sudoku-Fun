package com.veragames.sudokufun.domain.repository

import com.veragames.sudokufun.data.model.Cell
import com.veragames.sudokufun.domain.model.BoardSize
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {
    suspend fun loadBoard(size: BoardSize)

    suspend fun getBoard(): StateFlow<List<Cell>>

    suspend fun setCellValue(
        cell: Cell,
        value: Char,
        isHint: Boolean = false,
    ): Boolean

    suspend fun eraseCellValue(cell: Cell): Boolean

    suspend fun undoMovement(): Boolean

    suspend fun startChronometer()

    suspend fun pauseChronometer()

    suspend fun resumeChronometer()

    suspend fun isRunning(): StateFlow<Boolean>

    suspend fun stopChronometer()

    suspend fun getChronometer(): StateFlow<Long>

    suspend fun showHint(): Int

    suspend fun checkGameCompletion(): Boolean

    suspend fun noteValue(
        cell: Cell,
        value: Char,
    )

    suspend fun getMistakes(): StateFlow<Int>
}
