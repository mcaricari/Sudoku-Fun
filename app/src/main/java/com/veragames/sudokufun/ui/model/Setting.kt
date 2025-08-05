package com.veragames.sudokufun.ui.model

data class Setting(
    val iconId: Int,
    val titleId: Int,
    val key: String,
    val usesSwitch: Boolean = true,
    val booleanValue: Boolean = true,
    val stringValueId: Int? = null,
    val descriptionId: Int? = null,
    val checkedDescriptionId: Int? = null,
)
