package com.veragames.sudokufun.ui.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.veragames.sudokufun.ui.Dimens
import com.veragames.sudokufun.ui.model.AppTheme
import com.veragames.sudokufun.ui.theme.SudokuFunTheme
import com.veragames.sudokufun.ui.theme.green.greenLightScheme
import com.veragames.sudokufun.ui.theme.red.redLightScheme

@Composable
fun ThemeCircle(
    appTheme: AppTheme,
    selected: Boolean,
    onClick: (appTheme: AppTheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val color =
        when (appTheme) {
            AppTheme.GREEN -> greenLightScheme.primary
            AppTheme.RED -> redLightScheme.primary
            AppTheme.BLACK -> Color.Black
        }
    val borderColor = MaterialTheme.colorScheme.inversePrimary
    Canvas(
        modifier =
            modifier
                .padding(12.dp)
                .size(Dimens.THEME_CIRCLE_SIZE)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = {
                        onClick(appTheme)
                    },
                ),
    ) {
        val radius = size.minDimension / 2f
        if (selected) {
            drawCircle(
                color = borderColor,
                radius = radius.plus(Dimens.THEME_CIRCLE_BORDER_INCREMENT),
            )
        }
        drawCircle(
            color = color,
            radius = radius,
        )
    }
}

@Composable
fun ThemeSelector(
    currentTheme: AppTheme,
    onDismissRequest: () -> Unit,
    onThemeClick: (appTheme: AppTheme) -> Unit,
    offset: IntOffset,
    modifier: Modifier = Modifier,
) {
    Popup(
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
        offset = offset,
    ) {
        Row(
            modifier =
                modifier
                    .wrapContentSize()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(Dimens.GAME_BUTTON_CORNER_RADIUS),
                    ).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ThemeCircle(
                onClick = onThemeClick,
                appTheme = AppTheme.GREEN,
                selected = currentTheme == AppTheme.GREEN,
            )
            ThemeCircle(
                onClick = onThemeClick,
                appTheme = AppTheme.RED,
                selected = currentTheme == AppTheme.RED,
            )
            ThemeCircle(
                onClick = onThemeClick,
                appTheme = AppTheme.BLACK,
                selected = currentTheme == AppTheme.BLACK,
            )
        }
    }
}

@Preview
@Composable
private fun ThemeCirclePrev() {
    SudokuFunTheme {
        ThemeCircle(
            appTheme = AppTheme.RED,
            onClick = {},
            selected = false,
        )
    }
}

@Preview
@Composable
private fun ThemeSelectorPrev() {
    SudokuFunTheme {
        ThemeSelector(
            currentTheme = AppTheme.GREEN,
            onThemeClick = {},
            onDismissRequest = {},
            offset = IntOffset.Zero,
        )
    }
}
