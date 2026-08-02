package com.calculator.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.layout.WeightModifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextAlign
import androidx.compose.ui.text.overflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calculator.app.R
import com.calculator.app.logic.CalculatorEngine
import com.calculator.app.logic.Operator
import com.calculator.app.ui.theme.CalculatorTheme
import com.calculator.app.ui.theme.calculatorColors

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    CalculatorTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Display area
                DisplayArea(viewModel)

                // Button grid
                ButtonGrid(viewModel)
            }
        }
    }
}

@Composable
fun DisplayArea(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        // Secondary display (previous operation)
        viewModel.secondaryDisplay?.let { secondary ->
            Text(
                text = secondary,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = overflow.TextOverflow.Ellipsis
            )
        }

        // Main display
        Text(
            text = viewModel.displayValue,
            fontSize = 64.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.W300,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = overflow.TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ButtonGrid(viewModel: CalculatorViewModel) {
    val buttons = listOf(
        listOf(
            ButtonData(stringResource(R.string.clear), ButtonType.FUNCTION, CalculatorEngine.CalculatorInput.CLEAR),
            ButtonData(stringResource(R.string.plus_minus), ButtonType.FUNCTION, CalculatorEngine.CalculatorInput.PLUS_MINUS),
            ButtonData(stringResource(R.string.percent), ButtonType.FUNCTION, CalculatorEngine.CalculatorInput.PERCENT),
            ButtonData(stringResource(R.string.divide), ButtonType.OPERATOR, CalculatorEngine.CalculatorInput.Operator(Operator.DIVIDE))
        ),
        listOf(
            ButtonData(stringResource(R.string.seven), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(7)),
            ButtonData(stringResource(R.string.eight), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(8)),
            ButtonData(stringResource(R.string.nine), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(9)),
            ButtonData(stringResource(R.string.multiply), ButtonType.OPERATOR, CalculatorEngine.CalculatorInput.Operator(Operator.MULTIPLY))
        ),
        listOf(
            ButtonData(stringResource(R.string.four), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(4)),
            ButtonData(stringResource(R.string.five), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(5)),
            ButtonData(stringResource(R.string.six), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(6)),
            ButtonData(stringResource(R.string.subtract), ButtonType.OPERATOR, CalculatorEngine.CalculatorInput.Operator(Operator.SUBTRACT))
        ),
        listOf(
            ButtonData(stringResource(R.string.one), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(1)),
            ButtonData(stringResource(R.string.two), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(2)),
            ButtonData(stringResource(R.string.three), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(3)),
            ButtonData(stringResource(R.string.add), ButtonType.OPERATOR, CalculatorEngine.CalculatorInput.Operator(Operator.ADD))
        ),
        listOf(
            ButtonData(stringResource(R.string.zero), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Digit(0), span = 2),
            ButtonData(stringResource(R.string.decimal), ButtonType.NUMBER, CalculatorEngine.CalculatorInput.Decimal),
            ButtonData(stringResource(R.string.equals), ButtonType.EQUALS, CalculatorEngine.CalculatorInput.Equals)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { buttonData ->
                    val weight = if (buttonData.span == 2) 2f else 1f
                    CalculatorButton(
                        modifier = Modifier.weight(weight),
                        text = buttonData.text,
                        type = buttonData.type,
                        onClick = { viewModel.onInput(buttonData.input) }
                    )
                }
            }
        }
    }
}

data class ButtonData(
    val text: String,
    val type: ButtonType,
    val input: CalculatorEngine.CalculatorInput,
    val span: Int = 1
)

enum class ButtonType {
    NUMBER, OPERATOR, FUNCTION, EQUALS
}

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType,
    onClick: () -> Unit
) {
    val colors = when (type) {
        ButtonType.NUMBER -> ButtonDefaults.buttonColors(
            containerColor = calculatorColors.btnNumberBg,
            contentColor = calculatorColors.btnNumberText
        )
        ButtonType.OPERATOR -> ButtonDefaults.buttonColors(
            containerColor = calculatorColors.btnOperatorBg,
            contentColor = calculatorColors.btnOperatorText
        )
        ButtonType.FUNCTION -> ButtonDefaults.buttonColors(
            containerColor = calculatorColors.btnFunctionBg,
            contentColor = calculatorColors.btnFunctionText
        )
        ButtonType.EQUALS -> ButtonDefaults.buttonColors(
            containerColor = calculatorColors.btnEqualsBg,
            contentColor = calculatorColors.btnEqualsText
        )
    }

    val shape = RoundedCornerShape(28.dp)

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(4.dp),
        colors = colors,
        shape = shape,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp,
            hoveredElevation = 4.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center
        )
    }
}