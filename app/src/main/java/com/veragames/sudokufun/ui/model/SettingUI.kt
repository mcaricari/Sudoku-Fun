package com.veragames.sudokufun.ui.model

data class SettingUI(
    val iconId: Int,
    val titleId: Int,
    val key: String,
    val value: Boolean,
    val descriptionId: Int? = null,
    val checkedDescriptionId: Int? = null,
    val enabled: Boolean = true,
)
