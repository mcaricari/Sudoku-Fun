package com.veragames.sudokufun.ui.presentation.settingsscreen

import com.veragames.sudokufun.R
import com.veragames.sudokufun.data.preferences.PreferencesKeys
import com.veragames.sudokufun.ui.model.Setting

data class SettingsScreenState(
    val soundEffects: Boolean = true,
    val chronometer: Boolean = true,
    val mistakeLimit: Boolean = true,
    val numberLock: Boolean = true,
    val removeUsedNumbers: Boolean = true,
    // val appSettings: List<Setting> =
    //     listOf(
    //         Setting(
    //             iconId = R.drawable.icon_dark_mode,
    //             titleId = R.string.dark_mode,
    //             usesSwitch = false,
    //             stringValueId = R.string.dark_mode_enabled,
    //         ),
    //         Setting(
    //             iconId = R.drawable.icon_sound_effect,
    //             titleId = R.string.audio_effect,
    //             usesSwitch = true,
    //             booleanValue = soundEffects,
    //         ),
    //     ),
    val gameSettings: List<Setting> =
        listOf(
            Setting(
                iconId = R.drawable.icon_chronometer,
                titleId = R.string.chronometer,
                usesSwitch = true,
                booleanValue = chronometer,
                key = PreferencesKeys.CHRONOMETER,
                checkedDescriptionId = R.string.checked_description_chronometer_enabled,
            ),
            Setting(
                iconId = R.drawable.icon_warning,
                titleId = R.string.mistake_limit,
                usesSwitch = true,
                booleanValue = mistakeLimit,
                descriptionId = R.string.mistake_limit_description,
                key = PreferencesKeys.MISTAKE_LIMIT,
                checkedDescriptionId = R.string.checked_description_mistake_limit_enabled,
            ),
            Setting(
                iconId = R.drawable.icon_number,
                titleId = R.string.number_lock,
                usesSwitch = true,
                booleanValue = numberLock,
                descriptionId = R.string.number_lock_description,
                key = PreferencesKeys.NUMBER_LOCK,
                checkedDescriptionId = R.string.checked_description_number_lock_enabled,
            ),
            Setting(
                iconId = R.drawable.icon_number_done,
                titleId = R.string.remove_used_numbers,
                usesSwitch = true,
                booleanValue = removeUsedNumbers,
                descriptionId = R.string.remove_used_numbers_description,
                key = PreferencesKeys.REMOVE_USED_NUMBERS,
                checkedDescriptionId = R.string.checked_description_number_done_enabled,
            ),
        ),
)
