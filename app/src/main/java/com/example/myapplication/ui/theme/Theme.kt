package com.composeweatherapp.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.myapplication.ui.theme.Shapes
import com.example.myapplication.ui.theme.Typography

private val DarkColorPalette = darkColorScheme(
    primary = Black,

    secondary = TransparentDarkBlue,
    onSurface = WhiteTransparent,
    onSecondary = HighTransparentDarkBlue,

    error = ErrorRed
)

private val LightColorPalette = lightColorScheme(
    primary = Black,

    secondary = TransparentDarkBlue,
    onSurface = WhiteTransparent,
    onSecondary = HighTransparentDarkBlue,

    error = ErrorRed
)

@Composable
fun ComposeWeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }



    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}