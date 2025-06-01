package com.veragames.sudokufun.ui.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.veragames.sudokufun.data.preferences.AppTheme
import com.veragames.sudokufun.ui.navigation.AppNavHost
import com.veragames.sudokufun.ui.theme.SudokuFunTheme
import com.veragames.sudokufun.ui.theme.green.greenDarkScheme
import com.veragames.sudokufun.ui.theme.green.greenLightScheme
import com.veragames.sudokufun.ui.theme.red.redDarkScheme
import com.veragames.sudokufun.ui.theme.red.redLightScheme

@Composable
fun App(viewModel: MainViewModel = hiltViewModel()) {
    val state = viewModel.preferencesState.collectAsState()

    val theme =
        when (state.value.theme) {
            AppTheme.GREEN -> if (state.value.darkMode == true) greenDarkScheme else greenLightScheme
            AppTheme.RED -> if (state.value.darkMode == true) redDarkScheme else redLightScheme
            AppTheme.BLACK -> if (state.value.darkMode == true) greenDarkScheme else greenLightScheme
            null -> null
        }
    if (theme != null) {
        SudokuFunTheme(theme) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                AppNavHost(
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}
