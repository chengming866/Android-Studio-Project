package com.example.androidcompose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TaskRed = Color(0xFFC51A20)
val TaskRedPressed = Color(0xFFA91319)
val TextPrimary = Color(0xFF303030)
val TextSecondary = Color(0xFF666666)
val FieldBorder = Color(0xFFD5D5D5)
val CardBorder = Color(0xFFEEEEEE)

private val AndroidComposeColors = lightColorScheme(
    primary = TaskRed,
    onPrimary = Color.White,
    secondary = TaskRed,
    onSecondary = Color.White,
    background = Color.White,
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
)

@Composable
fun AndroidComposeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AndroidComposeColors,
        content = content,
    )
}

