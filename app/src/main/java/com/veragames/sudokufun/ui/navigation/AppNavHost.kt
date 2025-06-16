package com.veragames.sudokufun.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.veragames.sudokufun.ui.presentation.gamescreen.GameScreen
import com.veragames.sudokufun.ui.presentation.homescreen.HomeScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.GAME.name,
        modifier = modifier,
    ) {
        composable(Screen.MAIN.name) {
            HomeScreen(
                onNewGame = {
                    navController.navigate(Screen.GAME.name)
                },
            )
        }
        composable(Screen.GAME.name) {
            GameScreen(
                onBackToMainScreenClick = {
                    navController.navigate(Screen.MAIN.name)
                    navController.popBackStack(Screen.MAIN.name, inclusive = false)
                },
            )
        }
    }
}
