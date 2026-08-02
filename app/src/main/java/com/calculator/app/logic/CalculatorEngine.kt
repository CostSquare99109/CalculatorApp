package com.calculator.app.logic

import com.calculator.app.logic.CalculatorState.Error
import com.calculator.app.logic.CalculatorState.Ready
import com.calculator.app.logic.Operator.ADD
import com.calculator.app.logic.Operator.DIVIDE
import com.calculator.app.logic.Operator.MULTIPLY
import com.calculator.app.logic.Operator.SUBTRACT
import kotlin.math.abs

object CalculatorEngine {

    private const val MAX_DIGITS = 12
    private const val MAX_DECIMAL_PLACES = 10

    fun processInput(state: CalculatorState, input: CalculatorInput): CalculatorState {
        return when (state) {
            is Ready -> processReady(state, input)
            is Error -> if (input == CalculatorInput.CLEAR) Ready("0") else state
        }
    }

    private fun processReady(state: Ready, input: CalculatorInput): CalculatorState {
        return when (input) {
            is CalculatorInput.Digit -> handleDigit(state, input.digit)
            is CalculatorInput.Decimal -> handleDecimal(state)
            is CalculatorInput.Operator -> handleOperator(state, input.operator)
            is CalculatorInput.Equals -> handleEquals(state)
            CalculatorInput.CLEAR -> Ready("0")
            CalculatorInput.CLEAR_ENTRY -> Ready("0", state.previousValue, state.pendingOperator, false)
            CalculatorInput.BACKSPACE -> handleBackspace(state)
            CalculatorInput.PERCENT -> handlePercent(state)
            CalculatorInput.PLUS_MINUS -> handlePlusMinus(state)
        }
    }

    private fun handleDigit(state: Ready, digit: Int): Ready {
        val current = state.displayValue
        val newValue = if (state.isNewEntry || current == "0" || current.startsWith("Error")) {
            digit.toString()
        } else if (current.length < MAX_DIGITS) {
            current + digit
        } else {
            current
        }
        return state.copy(displayValue = newValue, isNewEntry = false)
    }

    private fun handleDecimal(state: Ready): Ready {
        if (state.isNewEntry) {
            return state.copy(displayValue = "0.", isNewEntry = false)
        }
        if (state.displayValue.contains(".").not()) {
            return state.copy(displayValue = state.displayValue + ".", isNewEntry = false)
        }
        return state
    }

    private fun handleOperator(state: Ready, inputOp: Operator): Ready {
        if (state.pendingOperator != null && state.previousValue != null && state.isNewEntry.not()) {
            val result = calculate(
                state.previousValue!!,
                state.displayValue,
                state.pendingOperator!!
            )
            if (result is Error) return result
            val resultValue = (result as Ready).displayValue.toDoubleOrNull()!!
            return Ready(
                displayValue = formatResult(resultValue),
                previousValue = formatResult(resultValue),
                pendingOperator = inputOp,
                isNewEntry = true
            )
        }
        return state.copy(
            previousValue = state.displayValue,
            pendingOperator = inputOp,
            isNewEntry = true
        )
    }

    private fun handleEquals(state: Ready): CalculatorState {
        if (state.pendingOperator != null && state.previousValue != null) {
            val result = calculate(state.previousValue!!, state.displayValue, state.pendingOperator!!)
            if (result is Error) return result
            val resultValue = (result as Ready).displayValue.toDoubleOrNull()!!
            return Ready(
                displayValue = formatResult(resultValue),
                previousValue = null,
                pendingOperator = null,
                isNewEntry = true
            )
        }
        return state
    }

    private fun handleBackspace(state: Ready): Ready {
        if (state.isNewEntry || state.displayValue == "0") return state
        val newValue = state.displayValue.dropLast(1)
        return state.copy(displayValue = if (newValue.isEmpty()) "0" else newValue)
    }

    private fun handlePercent(state: Ready): Ready {
        val value = state.displayValue.toDoubleOrNull() ?: return state
        val result = value / 100
        return state.copy(displayValue = formatResult(result), isNewEntry = true)
    }

    private fun handlePlusMinus(state: Ready): Ready {
        val value = state.displayValue.toDoubleOrNull() ?: return state
        val result = -value
        return state.copy(displayValue = formatResult(result))
    }

    private fun calculate(a: String, b: String, operator: Operator): CalculatorState {
        val x = a.toDoubleOrNull() ?: return Error("Error")
        val y = b.toDoubleOrNull() ?: return Error("Error")

        val result = when (operator) {
            ADD -> x + y
            SUBTRACT -> x - y
            MULTIPLY -> x * y
            DIVIDE -> if (y == 0.0) return Error("Error: División por cero") else x / y
        }

        if (result.isInfinite() || result.isNaN()) {
            return Error("Error: Desbordamiento")
        }
        if (abs(result) > 1e12) {
            return Error("Error: Desbordamiento")
        }

        return Ready(displayValue = formatResult(result))
    }

    private fun formatResult(value: Double): String {
        if (value == value.toLong().toDouble()) {
            val longVal = value.toLong()
            return if (abs(longVal) >= 1e12) {
                String.format("%.2e", value)
            } else {
                longVal.toString()
            }
        }
        val formatted = String.format("%.10f", value).replace(Regex("0+$"), "").replace(Regex("\\.$"), "")
        return if (formatted.length > MAX_DIGITS) {
            String.format("%.${MAX_DIGITS - 1}e", value)
        } else formatted
    }

    sealed interface CalculatorInput {
        data class Digit(val digit: Int) : CalculatorInput
        object Decimal : CalculatorInput
        data class Operator(val operator: Operator) : CalculatorInput
        object Equals : CalculatorInput
        object CLEAR : CalculatorInput
        object CLEAR_ENTRY : CalculatorInput
        object BACKSPACE : CalculatorInput
        object PERCENT : CalculatorInput
        object PLUS_MINUS : CalculatorInput
    }
}