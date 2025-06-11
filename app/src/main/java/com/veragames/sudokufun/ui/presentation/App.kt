package com.veragames.sudokufun.ui.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.veragames.sudokufun.ui.navigation.AppNavHost
import com.veragames.sudokufun.ui.theme.SudokuFunTheme

@Composable
fun App(
    systemInDarkTheme: Boolean = isSystemInDarkTheme(),
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    if (state.preferences.appTheme != null) {
        val darkMode =
            if (state.preferences.followSystemTheme) {
                systemInDarkTheme
            } else {
                state.preferences.darkMode
            }
        val colorScheme = state.preferences.appTheme!!.getColorScheme(darkMode)
        SudokuFunTheme(colorScheme) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                AppNavHost(
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}
