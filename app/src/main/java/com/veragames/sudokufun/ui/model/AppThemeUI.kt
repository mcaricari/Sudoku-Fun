package com.veragames.sudokufun.ui.model

import androidx.compose.material3.ColorScheme
import com.veragames.sudokufun.ui.theme.blue.blueDarkScheme
import com.veragames.sudokufun.ui.theme.blue.blueLightScheme
import com.veragames.sudokufun.ui.theme.green.greenDarkScheme
import com.veragames.sudokufun.ui.theme.green.greenLightScheme
import com.veragames.sudokufun.ui.theme.grey.greyDarkScheme
import com.veragames.sudokufun.ui.theme.grey.greyLightScheme
import com.veragames.sudokufun.ui.theme.red.redDarkScheme
import com.veragames.sudokufun.ui.theme.red.redLightScheme

enum class AppThemeUI {
    GREEN {
        override fun getColorScheme(darkTheme: Boolean): ColorScheme = if (darkTheme) greenDarkScheme else greenLightScheme
    },
    RED {
        override fun getColorScheme(darkTheme: Boolean): ColorScheme = if (darkTheme) redDarkScheme else redLightScheme
    },
    BLUE {
        override fun getColorScheme(darkTheme: Boolean): ColorScheme = if (darkTheme) blueDarkScheme else blueLightScheme
    },
    GREY {
        override fun getColorScheme(darkTheme: Boolean): ColorScheme = if (darkTheme) greyDarkScheme else greyLightScheme
    }, ;

    abstract fun getColorScheme(darkTheme: Boolean): ColorScheme
}
