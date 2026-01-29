package com.primetechterminal.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NeonGreen = Color(0xFF39FF14)
private val NeonPurple = Color(0xFFB026FF)
private val DarkBg = Color(0xFF0B0F0C)
private val DarkSurface = Color(0xFF121A14)

private val scheme = darkColorScheme(
    primary = NeonGreen,
    secondary = NeonPurple,
    background = DarkBg,
    surface = DarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = NeonGreen,
    onSurface = NeonGreen
)

@Composable
fun PrimeTechTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = scheme,
        typography = Typography(),
        content = content
    )
}
