package com.calculator.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.calculator.app.logic.CalculatorEngine
import com.calculator.app.logic.CalculatorState
import com.calculator.app.logic.CalculatorState.Error
import com.calculator.app.logic.CalculatorState.Ready

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf<CalculatorState>(Ready("0"))
        private set

    fun onInput(input: CalculatorEngine.CalculatorInput) {
        state = CalculatorEngine.processInput(state, input)
    }

    val displayValue: String
        get() {
            val currentState = state
            return when (currentState) {
                is Ready -> currentState.displayValue
                is Error -> currentState.message
            }
        }

    val secondaryDisplay: String?
        get() {
            val currentState = state
            return when (currentState) {
                is Ready -> buildSecondaryDisplay(currentState)
                is Error -> null
            }
        }

    private fun buildSecondaryDisplay(state: Ready): String? {
        val parts = mutableListOf<String>()
        state.previousValue?.let { parts.add(it) }
        state.pendingOperator?.let { parts.add(it.symbol) }
        return if (parts.isNotEmpty() && state.isNewEntry) parts.joinToString(" ") else null
    }
}