package com.veragames.sudokufun.ui.presentation.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veragames.sudokufun.ui.presentation.components.AppTitleImg
import com.veragames.sudokufun.ui.presentation.components.GeneralButton
import com.veragames.sudokufun.ui.theme.SudokuFunTheme
import com.veragames.sudokufun.ui.util.TestTags

@Composable
fun HomeScreen(onNewGame: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier =
            Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxSize(),
    ) {
        AppTitleImg(modifier = Modifier.fillMaxWidth().height(80.dp))
        GeneralButton(
            text = "Nuevo Juego",
            textColor = MaterialTheme.colorScheme.onPrimary,
            onClick = onNewGame,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.testTag(TestTags.START_GAME_BUTTON)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MainScreenPrev() {
    SudokuFunTheme {
        HomeScreen({})
    }
}
