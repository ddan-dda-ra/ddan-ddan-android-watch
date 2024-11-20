package com.ddanddan.watch.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import com.ddanddan.watch.presentation.ColorPalette_Dark
import com.ddanddan.watch.presentation.ColorPalette_Light
import com.ddanddan.watch.presentation.DDanDDanColorPalette

internal val wearColorPalette: Colors = Colors(
    primary = Color(48, 49, 51),
    primaryVariant = Color.LightGray,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onError = Color.Black
)

@Composable
fun PassiveDataTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = wearColorPalette,
        content = content
    )
}

@Composable
fun DDanDDanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorPalette = if (darkTheme) ColorPalette_Dark else ColorPalette_Light

    CompositionLocalProvider(DDanDDanColorPalette provides colorPalette) {
        content()
    }
}
