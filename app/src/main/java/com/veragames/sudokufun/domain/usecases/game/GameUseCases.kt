package com.veragames.sudokufun.domain.usecases.game

data class GameUseCases(
    val loadBoard: LoadBoard,
    val getBoard: GetBoard,
    val setCellValue: SetCellValue,
    val eraseCellValue: EraseCellValue,
    val undoMovement: UndoMovement,
    val startChronometer: StartChronometer,
    val pauseChronometer: PauseChronometer,
    val resumeChronometer: ResumeChronometer,
    val stopChronometer: StopChronometer,
    val getChronometer: GetChronometer,
    val checkIfGameIsRunning: CheckIfGameIsRunning,
    val showHint: ShowHint,
    val checkGameCompletion: CheckGameCompletion,
    val noteValue: NoteValue,
    val getMistakes: GetMistakes,
)
