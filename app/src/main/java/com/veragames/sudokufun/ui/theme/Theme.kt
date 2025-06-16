package com.veragames.sudokufun.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.veragames.sudokufun.ui.theme.green.greenLightScheme

@Composable
fun SudokuFunTheme(
    colorScheme: ColorScheme = greenLightScheme,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typo,
        content = content,
    )
}
