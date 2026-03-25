package com.fatupopz.staticfm.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val StaticFMColorScheme = darkColorScheme(
    primary      = Cyan,
    secondary    = Accent,
    tertiary     = SpotifyGreen,
    background   = Navy,
    surface      = NavyMid,
    onPrimary    = Navy,
    onBackground = TextPrimary,
    onSurface    = TextPrimary,
)

@Composable
fun StaticFMTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StaticFMColorScheme,
        typography  = Typography,
        content     = content
    )
}