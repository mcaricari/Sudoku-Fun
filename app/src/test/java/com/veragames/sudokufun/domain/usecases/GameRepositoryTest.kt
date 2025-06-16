package com.veragames.sudokufun.domain.usecases

import com.veragames.sudokufun.data.board.BoardSupplier
import com.veragames.sudokufun.data.board.mockedBoard
import com.veragames.sudokufun.data.board.mockedBoardSolved
import com.veragames.sudokufun.data.model.SudokuValue
import com.veragames.sudokufun.domain.model.BoardSize
import com.veragames.sudokufun.domain.repository.GameRepository
import com.veragames.sudokufun.domain.repository.GameRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class GameRepositoryTest {
    private lateinit var repository: GameRepository
    private val fakeBoardSupplier: BoardSupplier =
        mockk {
            every { getBoard(any()) } returns flowOf(mockedBoard)
            every { getSolvedBoard(any()) } returns flowOf(mockedBoardSolved)
        }

    @Before
    fun setUp() {
        repository = GameRepositoryImpl(fakeBoardSupplier)
        runTest {
            repository.loadBoard(BoardSize.NINE)
        }
    }

    @Test
    fun `set a 5 at cell 0,1`() {
        runTest {
            val cell = repository.getBoard().value[1]
            val result = repository.setCellValue(cell, SudokuValue.FIVE.value)
            assertTrue("Se debe poder setear el valor", result)
            assertEquals(SudokuValue.FIVE.value, repository.getBoard().value[1].value)
        }
    }

    @Test
    fun `cant set value on completed cell`() {
        runTest {
            val cell = repository.getBoard().value.first()
            val result = repository.setCellValue(cell, SudokuValue.FIVE.value)
            assertFalse("No se debe poder setear el valor en una celda completada", result)
        }
    }

    @Test
    fun `cant set value on user completed cell`() {
        runTest {
            val board = repository.getBoard()
            val cell = board.value[1]
            repository.setCellValue(cell, SudokuValue.FIVE.value)
            val expectedOldValue = board.value[1].value
            val result = repository.setCellValue(cell, SudokuValue.NINE.value)
            assertFalse("No se debe poder setear el valor en una celda completada", result)
            assertEquals(expectedOldValue, repository.getBoard().value[1].value)
        }
    }

    @Test
    fun `loads mocked board`() {
        runTest {
            repository.loadBoard(BoardSize.NINE)
            val board = repository.getBoard().value
            board.forEachIndexed { i, cell ->
                assertTrue("La fila y columna deben ser las mismas", cell.isSame(mockedBoard[i]))
                assertEquals(cell.value, mockedBoard[i].value)
            }
        }
    }

    @Test
    fun `erases user cell value`() {
        runTest {
            val cell = repository.getBoard().value[1]
            assertTrue(repository.setCellValue(cell, SudokuValue.FIVE.value))
            assertTrue(repository.eraseCellValue(cell))
            assertEquals(SudokuValue.EMPTY.value, repository.getBoard().value[1].value)
        }
    }

    @Test
    fun `cant erase a non user cell value`() {
        runTest {
            val cell = repository.getBoard().value[0]
            assertFalse(repository.eraseCellValue(cell))
        }
    }

    @Test
    fun `undoes a movement`() {
        runTest {
            val cell = repository.getBoard().value[1]
            repository.setCellValue(cell, SudokuValue.FOUR.value)
            repository.setCellValue(cell, SudokuValue.EIGHT.value)
            repository.setCellValue(cell, SudokuValue.SEVEN.value)
            assertEquals(SudokuValue.SEVEN.value, repository.getBoard().value[1].value)
            repository.undoMovement()
            assertEquals(SudokuValue.EIGHT.value, repository.getBoard().value[1].value)
            repository.undoMovement()
            assertEquals(SudokuValue.FOUR.value, repository.getBoard().value[1].value)
            repository.undoMovement()
            assertEquals(SudokuValue.EMPTY.value, repository.getBoard().value[1].value)
        }
    }

    @Test
    fun `chronometer starts at 0`() {
        runTest {
            assertEquals(0L, repository.getChronometer().value)
        }
    }

    @Test
    fun `chronometer starts`() {
        runTest {
            repository.startChronometer()
            Thread.sleep(1500)
            assertTrue(
                "El cronómetro debe ser mayor a un segundo",
                repository.getChronometer().value > 1000,
            )
        }
    }

    @Test
    fun `chronometer pauses`() {
        runTest {
            repository.startChronometer()
            Thread.sleep(1500)
            repository.pauseChronometer()
            Thread.sleep(2000)
            assertTrue(
                "El cronómetro debe ser menor que dos segundos",
                repository.getChronometer().value < 2000,
            )
        }
    }

    @Test
    fun `chronometer resumes`() {
        runTest {
            repository.startChronometer()
            Thread.sleep(1500)
            repository.pauseChronometer()
            Thread.sleep(2000)
            assertTrue(
                "El cronómetro debe ser menor que dos segundos",
                repository.getChronometer().value < 2000,
            )
            repository.resumeChronometer()
            Thread.sleep(1500)
            assertTrue(
                "El cronómetro debe ser mayor que dos segundos",
                repository.getChronometer().value > 2000,
            )
        }
    }

    @Test
    fun `hints are shown till max hints reached`() {
        runTest {
            val firstBoard = repository.getBoard().value
            repository.showHint()
            val secondBoard = repository.getBoard().value
            repository.showHint()
            val thirdBoard = repository.getBoard().value
            repository.showHint()
            val fourthBoard = repository.getBoard().value
            repository.showHint()
            val fifthBoard = repository.getBoard().value

            assertNotEquals(firstBoard, secondBoard)
            assertNotEquals(secondBoard, thirdBoard)
            assertNotEquals(thirdBoard, fourthBoard)
            assertEquals(fourthBoard, fifthBoard)
        }
    }

    @Test
    fun `checks game completion returns true when game is completed`() {
        runTest {
            mockedBoardSolved.forEach {
                repository.setCellValue(it, it.value)
            }
            assertTrue(repository.checkGameCompletion())
        }
    }

    @Test
    fun `notes and unnotes a cell note`() {
        runTest {
            repository.noteValue(repository.getBoard().value[1], SudokuValue.FIVE.value)
            assertTrue(
                repository
                    .getBoard()
                    .value[1]
                    .notes
                    .any { it.noted == true },
            )
            repository.noteValue(repository.getBoard().value[1], SudokuValue.FIVE.value)
            assertFalse(
                repository
                    .getBoard()
                    .value[1]
                    .notes
                    .any { it.noted == true },
            )
        }
    }

    @Test
    fun `notes are unnoted when cell is erased`() {
        runTest {
            repository.noteValue(repository.getBoard().value[1], SudokuValue.FIVE.value)
            repository.eraseCellValue(repository.getBoard().value[1])
            assertFalse(
                repository
                    .getBoard()
                    .value[1]
                    .notes
                    .any { it.noted == true },
            )
        }
    }

    @Test
    fun `notes are unnoted when a value is set`() {
        runTest {
            repository.noteValue(repository.getBoard().value[1], SudokuValue.FIVE.value)
            repository.setCellValue(repository.getBoard().value[1], SudokuValue.FIVE.value)
            assertFalse(
                repository
                    .getBoard()
                    .value[1]
                    .notes
                    .any { it.noted == true },
            )
        }
    }

    @Test
    fun `notes are unnoted when undoing a movement`() {
        runTest {
            repository.noteValue(repository.getBoard().value[1], SudokuValue.FIVE.value)
            repository.undoMovement()
            assertFalse(
                repository
                    .getBoard()
                    .value[1]
                    .notes
                    .any { it.noted == true },
            )
        }
    }
}
