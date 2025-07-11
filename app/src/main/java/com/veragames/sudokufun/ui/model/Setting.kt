package com.veragames.sudokufun.ui.model

data class Setting(
    val iconId: Int,
    val titleId: Int,
    val usesSwitch: Boolean = true,
    val booleanValue: Boolean = true,
    val stringValueId: Int? = null,
    val descriptionId: Int? = null,
)
