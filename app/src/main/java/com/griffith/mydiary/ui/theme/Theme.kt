package com.griffith.mydiary.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorWhite = Color(0xFFFFFFFF)

private val LightColors = lightColorScheme(
    primary = Amber,
    onPrimary = ColorWhite,
    primaryContainer = AmberSoft,
    onPrimaryContainer = Amber,
    secondary = InkSecondary,
    onSecondary = ColorWhite,
    secondaryContainer = WarmSurfaceVariant,
    onSecondaryContainer = Ink,
    background = Paper,
    onBackground = Ink,
    surface = SurfaceWhite,
    onSurface = Ink,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = InkSecondary,
    outline = WarmOutline,
    error = ErrorRed,
    onError = ColorWhite
)

private val DarkColors = darkColorScheme(
    primary = DarkAmber,
    onPrimary = DarkBackground,
    primaryContainer = DarkAmberSoft,
    onPrimaryContainer = Color(0xFFFCD9BE),
    secondary = DarkInkSecondary,
    onSecondary = DarkBackground,
    secondaryContainer = DarkSurface,
    onSecondaryContainer = DarkInk,
    background = DarkBackground,
    onBackground = DarkInk,
    surface = DarkSurface,
    onSurface = DarkInk,
    surfaceVariant = Color(0xFF2B2521),
    onSurfaceVariant = DarkInkSecondary,
    outline = DarkOutline,
    error = Color(0xFFF2B8B5),
    onError = DarkBackground
)

@Composable
fun MyDiaryTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
