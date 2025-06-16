package com.veragames.sudokufun.data.board

import com.veragames.sudokufun.data.model.Cell
import kotlinx.coroutines.flow.Flow

interface BoardSupplier {
    fun getBoard(size: Int): Flow<List<Cell>>

    fun getSolvedBoard(size: Int): Flow<List<Cell>>
}
