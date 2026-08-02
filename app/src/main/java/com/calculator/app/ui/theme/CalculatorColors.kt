package com.calculator.app.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val CalculatorColors = object {
    val light = CalculatorColorPalette(
        btnNumberBg = Color(0xFFFFFFFF),
        btnNumberText = Color(0xFF1D1B20),
        btnOperatorBg = Color(0xFFFF6B35),
        btnOperatorText = Color(0xFFFFFFFF),
        btnFunctionBg = Color(0xFFE0E0E8),
        btnFunctionText = Color(0xFF4A4A6A),
        btnEqualsBg = Color(0xFF006D77),
        btnEqualsText = Color(0xFFFFFFFF),
        displayBg = Color(0xFFFFFBFE),
        displayText = Color(0xFF1D1B20),
        displaySecondaryText = Color(0xFF79747E)
    )
    val dark = CalculatorColorPalette(
        btnNumberBg = Color(0xFF2D2B30),
        btnNumberText = Color(0xFFE5E1E6),
        btnOperatorBg = Color(0xFFFF6B35),
        btnOperatorText = Color(0xFFFFFFFF),
        btnFunctionBg = Color(0xFF3A3844),
        btnFunctionText = Color(0xFFC4C2E0),
        btnEqualsBg = Color(0xFF006D77),
        btnEqualsText = Color(0xFFFFFFFF),
        displayBg = Color(0xFF1D1B20),
        displayText = Color(0xFFE5E1E6),
        displaySecondaryText = Color(0xFF938F99)
    )
}

data class CalculatorColorPalette(
    val btnNumberBg: Color,
    val btnNumberText: Color,
    val btnOperatorBg: Color,
    val btnOperatorText: Color,
    val btnFunctionBg: Color,
    val btnFunctionText: Color,
    val btnEqualsBg: Color,
    val btnEqualsText: Color,
    val displayBg: Color,
    val displayText: Color,
    val displaySecondaryText: Color
)

val ColorScheme.calculatorColors: CalculatorColorPalette
    get() = if (this.isDark) CalculatorColors.dark else CalculatorColors.light