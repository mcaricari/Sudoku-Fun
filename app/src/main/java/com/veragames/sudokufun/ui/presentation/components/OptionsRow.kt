package com.veragames.sudokufun.ui.presentation.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    @DrawableRes iconId: Int? = null,
    @StringRes descriptionId: Int? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        if (iconId != null) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.padding(4.dp),
            )
        }
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(end = 32.dp, start = 12.dp),
        ) {
            CommonText(
                text = stringResource(textId),
                textAlign = TextAlign.Start,
            )
            if (descriptionId != null) {
                CommonText(
                    text = stringResource(descriptionId),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 6,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(4.dp),
                )
            }
        }
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

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL, showBackground = true, showSystemUi = false)
@Composable
private fun OptionsRowPreview() {
    SudokuFunTheme {
        OptionsRow(
            checked = true,
            textId = R.string.number_lock,
            onCheckedDescriptionId = android.R.string.ok,
            onCheckedChange = {},
            iconId = R.drawable.icon_number,
            descriptionId = R.string.number_lock_description,
        )
    }
}
