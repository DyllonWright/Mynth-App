package com.example.mynthapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography // ✅ Material 3
import androidx.compose.material3.Shapes // ✅ Material 3


// Obsidian color values
private val ObsidianColorScheme = darkColorScheme(
    primary = ObsidianAccent,
    secondary = ObsidianAccent,
    background = ObsidianBackground,
    surface = ObsidianSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = ObsidianText,
    onSurface = ObsidianText
)

// Optional fallback dark/light schemes (can be customized)
private val DefaultDarkColorScheme = darkColorScheme(
    primary = Purple200,
    secondary = Teal200,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val DefaultLightColorScheme = lightColorScheme(
    primary = Purple500,
    secondary = Teal200,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF2F2F2),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun MynthAppTheme(
    useObsidianTheme: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        useObsidianTheme -> ObsidianColorScheme
        darkTheme -> DefaultDarkColorScheme
        else -> DefaultLightColorScheme
    }
    val AppShapes = Shapes() // you can customize corners here too
    val AppTypography = Typography() // optionally override styles here
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,  // from material3
        shapes = AppShapes,          // from material3
        content = content
    )

}
