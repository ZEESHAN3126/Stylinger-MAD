package com.stylinger.app.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val StylingerColorScheme = lightColorScheme(
    primary = StylingerColors.BrandPrimary,
    onPrimary = StylingerColors.TextInverse,
    primaryContainer = StylingerColors.BrandTint,
    onPrimaryContainer = StylingerColors.BrandPrimary,
    secondary = StylingerColors.BrandSecondary,
    onSecondary = StylingerColors.TextInverse,
    background = StylingerColors.Canvas,
    onBackground = StylingerColors.TextPrimary,
    surface = StylingerColors.Surface,
    onSurface = StylingerColors.TextPrimary,
    surfaceVariant = StylingerColors.SurfaceElevated,
    onSurfaceVariant = StylingerColors.TextSecondary,
    outline = StylingerColors.Border
)

@Composable
fun StylingerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = StylingerColorScheme,
        typography = StylingerTypography,
        content = content
    )
}
