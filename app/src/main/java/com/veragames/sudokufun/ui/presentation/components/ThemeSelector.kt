package com.veragames.sudokufun.ui.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.veragames.sudokufun.R
import com.veragames.sudokufun.data.preferences.AppTheme
import com.veragames.sudokufun.ui.Dimens
import com.veragames.sudokufun.ui.theme.SudokuFunTheme
import com.veragames.sudokufun.ui.theme.blue.blueLightScheme
import com.veragames.sudokufun.ui.theme.green.greenLightScheme
import com.veragames.sudokufun.ui.theme.grey.greyLightScheme
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
            AppTheme.BLUE -> blueLightScheme.primary
            AppTheme.GREY -> greyLightScheme.primary
        }
    val borderColor = MaterialTheme.colorScheme.inversePrimary
    Canvas(
        modifier =
            modifier
                // .padding(12.dp)
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
fun ThemeOptionsRow(
    checked: Boolean,
    @StringRes textId: Int,
    @StringRes onCheckedDescriptionId: Int,
    onCheckedChange: (value: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        CommonText(
            text = stringResource(textId),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start,
        )
        Switch(
            checked = checked,
            enabled = enabled,
            onCheckedChange = { onCheckedChange(checked.not()) },
            thumbContent = {
                if (checked) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(onCheckedDescriptionId),
                    )
                }
            },
        )
    }
}

@Composable
fun ThemeSelector(
    currentTheme: AppTheme,
    darkModeEnabled: Boolean,
    systemDefaultThemeEnabled: Boolean,
    onDismissRequest: () -> Unit,
    onThemeClick: (appTheme: AppTheme) -> Unit,
    onDarkModeSwitchClick: (enable: Boolean) -> Unit,
    onSystemDefaultThemeSwitchClick: (enable: Boolean) -> Unit,
    offset: IntOffset,
    modifier: Modifier = Modifier,
) {
    Popup(
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
        offset = offset,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                modifier
                    .wrapContentHeight()
                    .width(Dimens.THEME_POPUP_WIDTH)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(Dimens.GAME_BUTTON_CORNER_RADIUS),
                    )
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
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
                    appTheme = AppTheme.BLUE,
                    selected = currentTheme == AppTheme.BLUE,
                )
                ThemeCircle(
                    onClick = onThemeClick,
                    appTheme = AppTheme.GREY,
                    selected = currentTheme == AppTheme.BLUE,
                )
            }
            ThemeOptionsRow(
                checked = darkModeEnabled,
                enabled = systemDefaultThemeEnabled.not(),
                textId = R.string.dark_mode,
                onCheckedDescriptionId = R.string.dark_mode_enabled,
                onCheckedChange = { onDarkModeSwitchClick(darkModeEnabled.not()) },
            )
            ThemeOptionsRow(
                checked = systemDefaultThemeEnabled,
                textId = R.string.system_default,
                onCheckedDescriptionId = R.string.system_default_theme_enabled,
                onCheckedChange = { onSystemDefaultThemeSwitchClick(systemDefaultThemeEnabled.not()) },
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
            darkModeEnabled = false,
            onDarkModeSwitchClick = {},
            systemDefaultThemeEnabled = true,
            onSystemDefaultThemeSwitchClick = {},
        )
    }
}
