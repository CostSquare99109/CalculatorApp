package com.calculator.app.logic

enum class Operator(val symbol: String) {
    ADD("+"),
    SUBTRACT("−"),
    MULTIPLY("×"),
    DIVIDE("÷")
}

sealed interface CalculatorState {
    data class Ready(
        val displayValue: String,
        val previousValue: String? = null,
        val pendingOperator: Operator? = null,
        val isNewEntry: Boolean = true
    ) : CalculatorState

    data class Error(
        val message: String
    ) : CalculatorState
}