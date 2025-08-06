package com.veragames.sudokufun.ui.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veragames.sudokufun.R
import com.veragames.sudokufun.ui.theme.SudokuFunTheme
import com.veragames.sudokufun.ui.util.TestTags

@Composable
fun BoardInfo(
    difficulty: String,
    currentTime: String,
    mistakes: Int,
    maxMistakes: Int,
    showChronometer: Boolean,
    mistakeLimit: Boolean,
    modifier: Modifier = Modifier,
) {
    val textColor = MaterialTheme.colorScheme.onSurface
    Row(
        modifier =
            modifier
                .padding(vertical = 24.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CommonText(
            text = difficulty,
            color = textColor,
            modifier =
                Modifier
                    .weight(1f)
                    .testTag(TestTags.DIFFICULTY_INFO),
        )
        if (showChronometer) {
            CommonText(
                text = currentTime,
                color = textColor,
                modifier =
                    Modifier
                        .weight(1f)
                        .testTag(TestTags.TIME_INFO),
            )
        }
        MistakesInfo(
            mistakes = mistakes,
            maxMistakes = maxMistakes,
            mistakeLimit = mistakeLimit,
            modifier =
                Modifier
                    .weight(1f)
                    .testTag(TestTags.MISTAKES_INFO),
        )
    }
}

@Composable
fun MistakesInfo(
    mistakes: Int,
    maxMistakes: Int,
    mistakeLimit: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
        )
        Spacer(Modifier.width(4.dp))
        CommonText(
            text = if (mistakeLimit) {
                stringResource(R.string.mistakes, mistakes, maxMistakes)
            } else {
                stringResource(R.string.mistakes_no_limits, mistakes)
            },
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = false,
    showBackground = true,
)
@Composable
private fun BoardInfoPrev() {
    SudokuFunTheme {
        BoardInfo(
            difficulty = "Easy",
            currentTime = "00:00",
            mistakes = 3,
            maxMistakes = 3,
            showChronometer = true,
            mistakeLimit = true,
        )
    }
}
