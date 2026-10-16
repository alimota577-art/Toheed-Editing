package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ToheedGold,
    onPrimary = ToheedDarkBlue,
    primaryContainer = ToheedCardBg,
    onPrimaryContainer = ToheedGoldBright,
    secondary = ToheedElectricBlue,
    onSecondary = Color.Black,
    secondaryContainer = ToheedNavySurface,
    onSecondaryContainer = Color.White,
    tertiary = WhatsAppGreen,
    onTertiary = Color.White,
    background = ToheedDeepNight,
    onBackground = ToheedTextPrimary,
    surface = ToheedDarkBlue,
    onSurface = ToheedTextPrimary,
    surfaceVariant = ToheedNavySurface,
    onSurfaceVariant = ToheedTextSecondary,
    outline = ToheedCardBorder
)

private val LightColorScheme = darkColorScheme(
    // Studio branding strictly thrives on dark blue, gold and black theme
    primary = ToheedGold,
    onPrimary = ToheedDarkBlue,
    primaryContainer = ToheedCardBg,
    onPrimaryContainer = ToheedGoldBright,
    secondary = ToheedElectricBlue,
    onSecondary = Color.Black,
    background = ToheedDeepNight,
    surface = ToheedDarkBlue,
    onBackground = ToheedTextPrimary,
    onSurface = ToheedTextPrimary,
    surfaceVariant = ToheedNavySurface,
    onSurfaceVariant = ToheedTextSecondary,
    outline = ToheedCardBorder
)

@Composable
fun ToheedEditsTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
