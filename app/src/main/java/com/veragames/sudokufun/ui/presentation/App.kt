package com.veragames.sudokufun.ui.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.veragames.sudokufun.ui.navigation.AppNavHost
import com.veragames.sudokufun.ui.theme.SudokuFunTheme

@Composable
fun App(viewModel: MainViewModel = hiltViewModel()) {
    SudokuFunTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavHost(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
