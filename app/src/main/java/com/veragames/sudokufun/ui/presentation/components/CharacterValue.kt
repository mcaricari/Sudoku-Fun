package com.veragames.sudokufun.ui.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.veragames.sudokufun.data.model.SudokuValue
import com.veragames.sudokufun.ui.Dimens
import com.veragames.sudokufun.ui.util.TestTags

@Composable
fun CharacterValue(
    value: SudokuValue,
    selected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptics = LocalHapticFeedback.current
    val containerColor: Color
    val textColor: Color
    if (selected) {
        containerColor = MaterialTheme.colorScheme.tertiaryContainer
        textColor = MaterialTheme.colorScheme.onTertiaryContainer
    } else {
        containerColor = Color.Transparent
        textColor = MaterialTheme.colorScheme.primary
    }
    Card(
        shape = RoundedCornerShape(Dimens.GAME_BUTTON_CORNER_RADIUS),
        colors = CardDefaults.cardColors().copy(containerColor = containerColor),
        modifier =
            modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        onLongClick()
                    },
                )
                .testTag(TestTags.getSudokuValueTestTag(value)),
    ) {
        CommonText(
            text = value.value.toString(),
            color = textColor,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun Preview_CharacterValue() {
    CharacterValue(
        value = SudokuValue.A,
        selected = false,
        onClick = {},
        onLongClick = {},
    )
}
