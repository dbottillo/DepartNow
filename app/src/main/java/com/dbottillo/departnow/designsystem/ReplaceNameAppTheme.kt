package com.dbottillo.departnow.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun DepartNowAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkAndroidColorScheme else LightAndroidColorScheme
    val customColors = if (darkTheme) DarkCustomColorsScheme else LightCustomColorsScheme
    CompositionLocalProvider(
        LocalCustomColors provides customColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

val LightCustomColorsScheme = CustomColors(
    top = Color.Yellow,
    bottom = Color.Blue
)

val DarkCustomColorsScheme = CustomColors(
    top = Color.Blue,
    bottom = Color.Yellow
)
