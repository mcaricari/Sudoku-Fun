package com.veragames.sudokufun.domain.di

import com.veragames.sudokufun.data.preferences.DataStoreRepository
import com.veragames.sudokufun.domain.repository.GameRepository
import com.veragames.sudokufun.domain.usecases.app.AppUseCases
import com.veragames.sudokufun.domain.usecases.app.GetUserPreferences
import com.veragames.sudokufun.domain.usecases.app.UpdateStringUserPreference
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    fun bindGameUseCases(gameRepository: GameRepository): GameUseCases =
        GameUseCases(
            loadBoard = LoadBoard(gameRepository),
            getBoard = GetBoard(gameRepository),
            setCellValue = SetCellValue(gameRepository),
            eraseCellValue = EraseCellValue(gameRepository),
            undoMovement = UndoMovement(gameRepository),
            startChronometer = StartChronometer(gameRepository),
            pauseChronometer = PauseChronometer(gameRepository),
            resumeChronometer = ResumeChronometer(gameRepository),
            stopChronometer = StopChronometer(gameRepository),
            getChronometer = GetChronometer(gameRepository),
            checkIfGameIsRunning = CheckIfGameIsRunning(gameRepository),
            showHint = ShowHint(gameRepository),
            checkGameCompletion = CheckGameCompletion(gameRepository),
            noteValue = NoteValue(gameRepository),
        )

    @Provides
    fun bindAppUseCases(dataStoreRepository: DataStoreRepository): AppUseCases =
        AppUseCases(
            getPreferences = GetUserPreferences(dataStoreRepository),
            updateStringUserPreference = UpdateStringUserPreference(dataStoreRepository),
        )
}
