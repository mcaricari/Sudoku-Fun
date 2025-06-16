package com.veragames.sudokufun.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.veragames.sudokufun.data.board.mockedBoard
import com.veragames.sudokufun.data.model.Note
import com.veragames.sudokufun.domain.model.CellStatus
import com.veragames.sudokufun.ui.Dimens
import com.veragames.sudokufun.ui.model.CellUI
import com.veragames.sudokufun.ui.theme.SudokuFunTheme
import com.veragames.sudokufun.ui.theme.green.userConflictCellText
import com.veragames.sudokufun.ui.util.TestTags
import com.veragames.sudokufun.ui.util.drawAllBorders
import com.veragames.sudokufun.ui.util.drawBottomBorder
import com.veragames.sudokufun.ui.util.drawRightBorder
import kotlin.math.sqrt

@Composable
fun Cell(
    cellUI: CellUI,
    onClick: (cell: CellUI) -> Unit,
    isGameRunning: Boolean,
    modifier: Modifier = Modifier,
) {
    var color: Color
    var textColor: Color

    when (cellUI.status) {
        CellStatus.NORMAL -> {
            color = MaterialTheme.colorScheme.secondaryContainer
            textColor = MaterialTheme.colorScheme.onSecondaryContainer
        }

        CellStatus.SELECTED -> {
            color = MaterialTheme.colorScheme.inversePrimary
            textColor = MaterialTheme.colorScheme.onPrimaryContainer
        }

        CellStatus.CONFLICT -> {
            color = MaterialTheme.colorScheme.error
            textColor = MaterialTheme.colorScheme.onError
        }

        CellStatus.COMMON_NUMBER -> {
            color = MaterialTheme.colorScheme.secondary
            textColor = MaterialTheme.colorScheme.onSecondary
        }

        CellStatus.IMPLICATED -> {
            color = MaterialTheme.colorScheme.tertiaryContainer
            textColor = MaterialTheme.colorScheme.onTertiaryContainer
        }
    }

    if (cellUI.cell.userCell && cellUI.cell.conflict) {
        textColor = userConflictCellText
    }

    if (cellUI.cell.userCell && cellUI.cell.completed) {
        textColor = MaterialTheme.colorScheme.primary
        if (cellUI.status == CellStatus.COMMON_NUMBER) {
            textColor = MaterialTheme.colorScheme.onSecondary
        }
    }

    if (isGameRunning.not()) {
        color = MaterialTheme.colorScheme.onSurfaceVariant
        textColor = MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier =
            modifier
                .background(color)
                .clickable { onClick(cellUI) },
        contentAlignment = Alignment.Center,
    ) {
        if (cellUI.cell.notes.any { it.noted == true }) {
            NotesGrid(cellUI = cellUI)
        } else {
            CommonText(
                text = cellUI.cell.value.toString(),
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
fun NotesGrid(
    cellUI: CellUI,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns =
            GridCells.Fixed(
                sqrt(
                    cellUI.cell.notes.size
                        .toDouble(),
                ).toInt(),
            ),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.wrapContentSize(),
    ) {
        items(cellUI.cell.notes) { note ->
            Note(
                note = note,
                modifier =
                    Modifier
                        .aspectRatio(1f)
                        .testTag(TestTags.getCellNoteTestTag(cellUI, note.value)),
            )
        }
    }
}

@Composable
fun Note(
    note: Note,
    modifier: Modifier = Modifier,
) {
    val color =
        if (note.noted) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            Color.Transparent
        }
    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.Center,
    ) {
        CommonText(
            text = note.value.value.toString(),
            color = color,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
fun Board(
    cellList: List<CellUI>,
    onCellClick: (cell: CellUI) -> Unit,
    isGameRunning: Boolean,
    modifier: Modifier = Modifier,
) {
    if (cellList.isNotEmpty()) {
        val boardColumns = sqrt(cellList.size.toDouble()).toInt()
        val boxSize = sqrt(boardColumns.toDouble()).toInt()
        val borderColor = MaterialTheme.colorScheme.outline
        LazyVerticalGrid(
            columns = GridCells.Fixed(boardColumns),
            userScrollEnabled = false,
            modifier =
                modifier
                    .wrapContentSize()
                    .border(Dimens.BOARD_BORDER_DP, borderColor),
        ) {
            items(cellList) { cellUI ->
                Cell(
                    cellUI = cellUI,
                    onClick = onCellClick,
                    isGameRunning = isGameRunning,
                    modifier =
                        Modifier
                            .aspectRatio(1f)
                            .drawWithContent {
                                drawContent()
                                drawAllBorders(borderColor, Dimens.CELL_BORDER_WIDTH_NORMAL)

                                // Engrosado de bordes
                                if ((cellUI.cell.col + 1) % boxSize == 0 && cellUI.cell.col < boardColumns - 1) {
                                    drawRightBorder(borderColor, Dimens.CELL_BORDER_WIDTH_THICK)
                                }
                                if ((cellUI.cell.row + 1) % boxSize == 0) {
                                    drawBottomBorder(borderColor, Dimens.CELL_BORDER_WIDTH_THICK)
                                }
                            }.testTag(TestTags.getCellTestTag(cellUI)),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BoardPrev() {
    SudokuFunTheme {
        Board(mockedBoard.map { CellUI(it) }, {}, true)
    }
}
