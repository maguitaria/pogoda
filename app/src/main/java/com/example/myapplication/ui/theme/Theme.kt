package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = Pink,
    secondary = SecondaryColor,
    onSurface = SurfaceColor,
    onSecondary = OnSecondaryColor,

    error = SurfaceColor
)

private val LightColorPalette = lightColorScheme(
    primary = Pink,

    secondary = SecondaryColor,
    onSurface = SurfaceColor,
    onSecondary = OnSecondaryColor,
    onBackground = BackgroundColor,

    error = SurfaceColor
)

@Composable
fun ComposeWeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }



    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}