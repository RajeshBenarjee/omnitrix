package com.bio.omnitrix.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

val OmnitrixGreen = Color(0xFF39FF14)
val OmnitrixDarkGreen = Color(0xFF006400)
val OmnitrixBlack = Color(0xFF000000)

private val OmnitrixColorPalette = Colors(
    primary = OmnitrixGreen,
    primaryVariant = OmnitrixDarkGreen,
    secondary = Color.White,
    background = OmnitrixBlack,
    surface = Color.DarkGray,
    onPrimary = OmnitrixBlack,
    onSecondary = OmnitrixBlack,
    onBackground = OmnitrixGreen,
    onSurface = OmnitrixGreen,
)

@Composable
fun OmnitrixTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = OmnitrixColorPalette,
        content = content
    )
}
