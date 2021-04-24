package com.mayurg.jetchess.util.themeutils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable


val appThemeColors = darkColors(
    primary = primaryColor,
    primaryVariant = primaryColorVariant,
    onPrimary = onPrimaryColor,
    secondary = secondaryColor,
    secondaryVariant = secondaryColorVariant,
    onSecondary = onSecondaryColor,
    surface = surface,
    background = background,
    onBackground = onBackground,
    onSurface = onSurface,
    error = errorColor,
    onError = onErrorColor
)

@Composable
fun AppTheme(content: @Composable() () -> Unit) {

    MaterialTheme(
        colors = appThemeColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}