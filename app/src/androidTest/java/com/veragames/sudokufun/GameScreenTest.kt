package com.veragames.sudokufun

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import com.veragames.sudokufun.data.FakeBoardSupplier
import com.veragames.sudokufun.data.board.mockedBoardSolved
import com.veragames.sudokufun.data.model.Cell
import com.veragames.sudokufun.data.model.SudokuValue
import com.veragames.sudokufun.domain.repository.GameRepository
import com.veragames.sudokufun.domain.repository.GameRepositoryImpl
import com.veragames.sudokufun.domain.usecases.game.CheckGameCompletion
import com.veragames.sudokufun.domain.usecases.game.CheckIfGameIsRunning
import com.veragames.sudokufun.domain.usecases.game.EraseCellValue
import com.veragames.sudokufun.domain.usecases.game.GameUseCases
import com.veragames.sudokufun.domain.usecases.game.GetBoard
import com.veragames.sudokufun.domain.usecases.game.GetChronometer
import com.veragames.sudokufun.domain.usecases.game.LoadBoard
import com.veragames.sudokufun.domain.usecases.game.NoteValue
import com.veragames.sudokufun.domain.usecases.game.PauseChronometer
import com.veragames.sudokufun.domain.usecases.game.ResumeChronometer
import com.veragames.sudokufun.domain.usecases.game.SetCellValue
import com.veragames.sudokufun.domain.usecases.game.ShowHint
import com.veragames.sudokufun.domain.usecases.game.StartChronometer
import com.veragames.sudokufun.domain.usecases.game.StopChronometer
import com.veragames.sudokufun.domain.usecases.game.UndoMovement
import com.veragames.sudokufun.ui.model.CellUI
import com.veragames.sudokufun.ui.presentation.gamescreen.GameScreen
import com.veragames.sudokufun.ui.presentation.gamescreen.GameViewModel
import com.veragames.sudokufun.ui.theme.SudokuFunTheme
import com.veragames.sudokufun.ui.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GameScreenTest {
    private lateinit var repository: GameRepository
    private lateinit var loadBoardUseCase: LoadBoard
    private lateinit var setCellValueUseCase: SetCellValue
    private lateinit var getBoardUseCase: GetBoard
    private lateinit var eraseCellValueUseCase: EraseCellValue
    private lateinit var undoMovementUseCase: UndoMovement
    private lateinit var startChronometer: StartChronometer
    private lateinit var pauseChronometer: PauseChronometer
    private lateinit var resumeChronometer: ResumeChronometer
    private lateinit var stopChronometer: StopChronometer
    private lateinit var getChronometer: GetChronometer
    private lateinit var checkIfGameIsRunning: CheckIfGameIsRunning
    private lateinit var showHint: ShowHint
    private lateinit var checkGameCompletion: CheckGameCompletion
    private lateinit var noteValue: NoteValue
    private lateinit var gameViewModel: GameViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        repository = GameRepositoryImpl(FakeBoardSupplier())
        loadBoardUseCase = LoadBoard(repository)
        setCellValueUseCase = SetCellValue(repository)
        getBoardUseCase = GetBoard(repository)
        eraseCellValueUseCase = EraseCellValue(repository)
        undoMovementUseCase = UndoMovement(repository)
        startChronometer = StartChronometer(repository)
        pauseChronometer = PauseChronometer(repository)
        resumeChronometer = ResumeChronometer(repository)
        stopChronometer = StopChronometer(repository)
        getChronometer = GetChronometer(repository)
        checkIfGameIsRunning = CheckIfGameIsRunning(repository)
        showHint = ShowHint(repository)
        checkGameCompletion = CheckGameCompletion(repository)
        noteValue = NoteValue(repository)
        gameViewModel =
            GameViewModel(
                GameUseCases(
                    loadBoardUseCase,
                    getBoardUseCase,
                    setCellValueUseCase,
                    eraseCellValueUseCase,
                    undoMovementUseCase,
                    startChronometer,
                    pauseChronometer,
                    resumeChronometer,
                    stopChronometer,
                    getChronometer,
                    checkIfGameIsRunning,
                    showHint,
                    checkGameCompletion,
                    noteValue,
                ),
            )

        composeTestRule.setContent {
            SudokuFunTheme {
                GameScreen(
                    onBackToMainScreenClick = {},
                    viewModel = gameViewModel,
                )
            }
        }
    }

    @Test
    fun loads_board_on_screen() {
        gameViewModel.state.value.board.forEach {
            composeTestRule
                .onNodeWithTag(getCellUiTag(it.cell.row, it.cell.col))
                .assertExists("La celda ${it.cell.row}, ${it.cell.col} no se muestra")
        }
    }

    @Test
    fun sets_value_on_board() {
        composeTestRule.onNodeWithTag(getCellUiTag(3, 4)).performClick()
        composeTestRule
            .onNodeWithTag(TestTags.getSudokuValueTestTag(SudokuValue.FIVE))
            .performClick()
        composeTestRule.onNodeWithTag(getCellUiTag(3, 4)).assertTextContains("5")
    }

    @Test
    fun erases_value_on_board() {
        sets_value_on_board()
        composeTestRule.onNodeWithTag(getCellUiTag(3, 4)).performClick()
        composeTestRule.onNodeWithTag(TestTags.ERASE_BUTTON).performClick()
        composeTestRule.onNodeWithTag(getCellUiTag(3, 4)).assertTextContains(" ")
    }

    @Test
    fun undoes_value_on_board() {
        composeTestRule.apply {
            onNodeWithTag(getCellUiTag(0, 1)).performClick()
            onNodeWithTag(TestTags.getSudokuValueTestTag(SudokuValue.FOUR)).performClick()
            onNodeWithTag(TestTags.getSudokuValueTestTag(SudokuValue.EIGHT)).performClick()
            onNodeWithTag(TestTags.getSudokuValueTestTag(SudokuValue.SEVEN)).performClick()
            onNodeWithTag(getCellUiTag(0, 1)).assertTextContains("7")
            onNodeWithTag(TestTags.UNDO_BUTTON).performClick()
            onNodeWithTag(getCellUiTag(0, 1)).assertTextContains("8")
            onNodeWithTag(TestTags.UNDO_BUTTON).performClick()
            onNodeWithTag(getCellUiTag(0, 1)).assertTextContains("4")
            onNodeWithTag(TestTags.UNDO_BUTTON).performClick()
            onNodeWithTag(getCellUiTag(0, 1)).assertTextContains(" ")
        }
    }

    @Test
    fun chronometer_starts_when_game_starts() {
        composeTestRule.apply {
            mainClock.advanceTimeBy(3000)
            onNode(hasText("00:00")).isNotDisplayed()
        }
    }

    @Test
    fun chronometer_stops() {
        composeTestRule.apply {
            mainClock.advanceTimeBy(3000)
            onNodeWithTag(TestTags.PAUSE_BUTTON).performClick()
            val time = gameViewModel.state.value.time
            mainClock.advanceTimeBy(2000)
            onNodeWithTag(TestTags.TIME_INFO).assertTextContains(time)
        }
    }

    @Test
    fun hint_button_disables_after_max_hints_reached() {
        composeTestRule.apply {
            onNodeWithTag(TestTags.HINT_BUTTON).performClick()
            onNodeWithTag(TestTags.HINT_BUTTON).performClick()
            onNodeWithTag(TestTags.HINT_BUTTON).performClick()
            onNodeWithTag(TestTags.HINT_BUTTON).assertIsNotEnabled()
        }
    }

    @Test
    fun game_completes_and_shows_dialog() {
        composeTestRule.apply {
            mockedBoardSolved.filter { it.userCell == true }.forEach {
                onNodeWithTag(getCellUiTag(it.row, it.col)).performClick()
                onNodeWithTag(getSudokuValueTag(it.value)).performClick()
            }
            onNodeWithTag(TestTags.GO_TO_MAIN_SCREEN_BUTTON).assertExists()
        }
    }

    @Test
    fun notes_are_shown_after_enabling_notes() {
        composeTestRule.apply {
            val cell = CellUI(Cell(' ', 0, 1, 0))
            onNodeWithTag(TestTags.NOTES_BUTTON).performClick()
            onNodeWithTag(TestTags.getCellTestTag(cell)).performClick()
            onNodeWithTag(TestTags.getSudokuValueTestTag(SudokuValue.FIVE)).performClick()
            onNodeWithTag(TestTags.getCellNoteTestTag(cell, SudokuValue.FIVE), true).assertExists()
        }
    }

    @Test
    fun set_value_when_value_is_selected_and_cell_clicked() {
        composeTestRule.apply {
            onNodeWithTag(TestTags.getSudokuValueTestTag(SudokuValue.FIVE)).performTouchInput {
                longClick()
            }
            onNodeWithTag(getCellUiTag(0, 7)).performClick()
            onNodeWithTag(getCellUiTag(0, 7)).assertTextContains("5")
            onNodeWithTag(getCellUiTag(4, 1)).performClick()
            onNodeWithTag(getCellUiTag(4, 1)).assertTextContains("5")
        }
    }

    private fun getCellUiTag(
        row: Int,
        col: Int,
    ) = TestTags.getCellTestTag(CellUI(Cell(' ', row, col, 0)))

    private fun getSudokuValueTag(value: Char): String {
        val sudokuValue = SudokuValue.entries.find { it.value == value }
        return TestTags.getSudokuValueTestTag(sudokuValue!!)
    }
}
