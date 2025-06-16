package com.veragames.sudokufun.ui.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.veragames.sudokufun.R
import com.veragames.sudokufun.ui.Dimens
import com.veragames.sudokufun.ui.theme.SudokuFunTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    userScore: Int,
    onBackClick: () -> Unit,
    onThemeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    setIconThemePosition: (Offset) -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            CommonText(
                text = userScore.toString(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.pause_game_and_go_back_content_desc),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(Dimens.TOP_BAR_ICON_SIZE),
                )
            }
        },
        actions = {
            IconButton(
                onClick = onThemeClick,
                modifier =
                    Modifier.onGloballyPositioned { coordinates ->
                        setIconThemePosition(coordinates.positionInWindow())
                    },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_theme),
                    contentDescription = stringResource(R.string.select_theme_content_desc),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(Dimens.TOP_BAR_ICON_SIZE),
                )
            }
            IconButton(
                onClick = onSettingsClick,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_settings),
                    contentDescription = stringResource(R.string.open_settings_content_desc),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(Dimens.TOP_BAR_ICON_SIZE),
                )
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun TopAppBarPrev() {
    SudokuFunTheme {
        GameTopBar(
            userScore = 50,
            onBackClick = {},
            onThemeClick = {},
            onSettingsClick = {},
            setIconThemePosition = {},
        )
    }
}
