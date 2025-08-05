package com.veragames.sudokufun.ui.presentation.settingsscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.veragames.sudokufun.R
import com.veragames.sudokufun.data.preferences.PreferencesKeys
import com.veragames.sudokufun.ui.Dimens
import com.veragames.sudokufun.ui.model.SettingUI
import com.veragames.sudokufun.ui.presentation.components.CommonText
import com.veragames.sudokufun.ui.presentation.components.OptionsRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onGoBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val settings: List<SettingUI> =
        listOf(
            SettingUI(
                iconId = R.drawable.icon_dark_mode,
                titleId = R.string.dark_mode,
                key = PreferencesKeys.DARK_MODE,
                checkedDescriptionId = R.string.checked_description_dark_mode_enabled,
                value = state.darkMode,
                enabled = state.systemTheme.not(),
            ),
            SettingUI(
                iconId = R.drawable.icon_dark_mode,
                titleId = R.string.system_default,
                key = PreferencesKeys.FOLLOW_SYSTEM_THEME,
                checkedDescriptionId = R.string.checked_description_sync_system_theme,
                value = state.systemTheme,
            ),
            SettingUI(
                iconId = R.drawable.icon_chronometer,
                titleId = R.string.chronometer,
                key = PreferencesKeys.CHRONOMETER,
                checkedDescriptionId = R.string.checked_description_chronometer_enabled,
                value = state.chronometer,
            ),
            SettingUI(
                iconId = R.drawable.icon_warning,
                titleId = R.string.mistake_limit,
                descriptionId = R.string.mistake_limit_description,
                key = PreferencesKeys.MISTAKE_LIMIT,
                checkedDescriptionId = R.string.checked_description_mistake_limit_enabled,
                value = state.mistakeLimit,
            ),
            SettingUI(
                iconId = R.drawable.icon_number,
                titleId = R.string.number_lock,
                descriptionId = R.string.number_lock_description,
                key = PreferencesKeys.NUMBER_LOCK,
                checkedDescriptionId = R.string.checked_description_number_lock_enabled,
                value = state.numberLock,
            ),
            SettingUI(
                iconId = R.drawable.icon_number_done,
                titleId = R.string.remove_used_numbers,
                descriptionId = R.string.remove_used_numbers_description,
                key = PreferencesKeys.REMOVE_USED_NUMBERS,
                checkedDescriptionId = R.string.checked_description_number_done_enabled,
                value = state.removeUsedNumbers,
            ),
        )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    CommonText(
                        text = stringResource(id = R.string.settings_screen_title),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onGoBack,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.settings_go_back_content_desc),
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(Dimens.TOP_BAR_ICON_SIZE),
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(settings) { setting ->
                OptionsRow(
                    checked = setting.value,
                    textId = setting.titleId,
                    descriptionId = setting.descriptionId,
                    onCheckedChange = {
                        viewModel.updatePreference(setting.key, it)
                    },
                    onCheckedDescriptionId = setting.checkedDescriptionId!!,
                    enabled = setting.enabled,
                    iconId = setting.iconId,
                )
            }
        }
    }
}
