package com.veragames.sudokufun.ui.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.veragames.sudokufun.R
import com.veragames.sudokufun.ui.theme.SudokuFunTheme

@Composable
fun OptionsRow(
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

@Preview
@Composable
private fun OptionsRowPreview() {
    SudokuFunTheme {
        OptionsRow(
            checked = true,
            textId = R.string.dark_mode_enabled,
            onCheckedDescriptionId = android.R.string.ok,
            onCheckedChange = {},
        )
    }
}
