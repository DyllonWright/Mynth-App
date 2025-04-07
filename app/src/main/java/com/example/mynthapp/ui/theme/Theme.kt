package com.example.mynthapp.ui.theme

import com.example.mynthapp.ui.theme.ObsidianAccent
import com.example.mynthapp.ui.theme.ObsidianBackground
import com.example.mynthapp.ui.theme.ObsidianSurface
import com.example.mynthapp.ui.theme.ObsidianText

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mynthapp.ui.theme.AppTypography
import com.example.mynthapp.ui.theme.Shapes

private val ObsidianColorPalette = darkColors(
    primary = ObsidianAccent,
    primaryVariant = ObsidianAccent,
    secondary = ObsidianAccent,
    background = ObsidianBackground,
    surface = ObsidianSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = ObsidianText,
    onSurface = ObsidianText
)
private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
    // Add other default colors to override
)

@Composable
fun MynthAppTheme(
    useObsidianTheme: Boolean = true, // <-- change this to false if needed
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when {
        useObsidianTheme -> ObsidianColorPalette
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}

