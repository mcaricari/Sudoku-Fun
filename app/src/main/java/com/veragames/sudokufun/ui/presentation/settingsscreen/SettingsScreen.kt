package com.veragames.sudokufun.ui.presentation.settingsscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.veragames.sudokufun.ui.presentation.components.OptionsRow

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
        ) {
            items(state.gameSettings) { setting ->
                OptionsRow(
                    checked = setting.booleanValue,
                    textId = setting.titleId,
                    descriptionId = setting.descriptionId,
                    onCheckedChange = {
                        viewModel.updatePreference(setting.key, it)
                    },
                    onCheckedDescriptionId = setting.checkedDescriptionId!!,
                )
            }
        }
    }
}
