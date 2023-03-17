package com.dbottillo.replacename.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColors(
    val top: Color = Color.Unspecified,
    val bottom: Color = Color.Unspecified
)

/**
 * A composition local for [CustomColors].
 */
val LocalCustomColors = staticCompositionLocalOf { CustomColors() }
