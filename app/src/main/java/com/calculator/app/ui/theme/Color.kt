package com.calculator.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB499),
    primaryContainer = Color(0xFF4D2000),
    onPrimary = Color(0xFF1A0D00),
    onPrimaryContainer = Color(0xFFFFE5D9),
    secondary = Color(0xFFC4C2E0),
    secondaryContainer = Color(0xFF32324E),
    onSecondary = Color(0xFF0C0C26),
    onSecondaryContainer = Color(0xFFE0E0E8),
    tertiary = Color(0xFF99D9E0),
    tertiaryContainer = Color(0xFF00383C),
    onTertiary = Color(0xFF001E20),
    onTertiaryContainer = Color(0xFFD4F1F4),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF690005),
    onError = Color(0xFF370001),
    onErrorContainer = Color(0xFFFFDAD6),
    surface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFF49454F),
    onSurface = Color(0xFFE5E1E6),
    onSurfaceVariant = Color(0xFFCAC4D0),
    background = Color(0xFF1D1B20),
    onBackground = Color(0xFFE5E1E6),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454F),
    inverseSurface = Color(0xFFE5E1E6),
    inverseOnSurface = Color(0xFF322F35),
    inversePrimary = Color(0xFFFF6B35),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6B35),
    primaryContainer = Color(0xFFFFE5D9),
    onPrimary = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF2D1300),
    secondary = Color(0xFF4A4A6A),
    secondaryContainer = Color(0xFFE0E0E8),
    onSecondary = Color(0xFFFFFFFF),
    onSecondaryContainer = Color(0xFF1E1E3A),
    tertiary = Color(0xFF006D77),
    tertiaryContainer = Color(0xFFD4F1F4),
    onTertiary = Color(0xFFFFFFFF),
    onTertiaryContainer = Color(0xFF002023),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF410002),
    surface = Color(0xFFFFFBFE),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurface = Color(0xFF1D1B20),
    onSurfaceVariant = Color(0xFF49454F),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1D1B20),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    inverseSurface = Color(0xFF322F35),
    inverseOnSurface = Color(0xFFF5F0F6),
    inversePrimary = Color(0xFFFFB499),
)

@Composable
fun CalculatorTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}