package com.konstde00.lab_1.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konstde00.lab_1.viewmodel.MainViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CoefficientInput(
    onSolve: (List<Double>) -> Unit,
    onSave: (List<String>) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel()
    val numberOfEquations by viewModel.numberOfEquations.collectAsState()
    val coefficientsList by viewModel.coefficientsList.collectAsState()
    val context = LocalContext.current

    val fieldsPerEquation = if (numberOfEquations == 3) 4 else 3

    val coefficients = remember {
        mutableStateListOf(*Array(numberOfEquations * fieldsPerEquation) { "" })
    }

    val errorStates = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(numberOfEquations * fieldsPerEquation) { add(false) }
        }
    }

    LaunchedEffect(coefficientsList, numberOfEquations) {
        coefficients.clear()
        coefficients.addAll(coefficientsList.map { it.toString() })
        if (coefficients.size < numberOfEquations * fieldsPerEquation) {
            repeat(numberOfEquations * fieldsPerEquation - coefficients.size) {
                coefficients.add("")
                errorStates.add(false)
            }
        } else if (coefficients.size > numberOfEquations * fieldsPerEquation) {
            repeat(coefficients.size - numberOfEquations * fieldsPerEquation) {
                coefficients.removeLast()
                errorStates.removeLast()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Enter Coefficients for the System of Equations",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        for (i in 0 until numberOfEquations) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (j in 0 until fieldsPerEquation) {
                    val index = i * fieldsPerEquation + j
                    OutlinedTextField(
                        value = if (coefficients.size > index) coefficients[index] else "",
                        onValueChange = { newValue ->
                            if (coefficients.size > index) {
                                coefficients[index] = newValue
                                errorStates[index] = newValue.isBlank() || newValue.toDoubleOrNull() == null
                            } else {
                                coefficients.add(newValue)
                                errorStates.add(newValue.isBlank() || newValue.toDoubleOrNull() == null)
                            }
                        },
                        label = {
                            when (j) {
                                0 -> Text("x${i + 1}")
                                1 -> Text("y${i + 1}")
                                2 -> {
                                    if (numberOfEquations == 3) {
                                        Text("z${i + 1}")
                                    } else {
                                        Text("d${i + 1}")
                                    }
                                }
                                3 -> Text("d${i + 1}")
                                else -> Text("")
                            }
                        },
                        isError = if (coefficients.size > index) errorStates[index] else false,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        supportingText = {
                            if (coefficients.size > index && errorStates[index]) {
                                Text(
                                    text = "Invalid number",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red
                        ),
                        modifier = Modifier
                            .width(85.dp)
                            .padding(end = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { viewModel.addEquation() },
                enabled = numberOfEquations < 3,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Add Equation")
            }

            Button(
                onClick = { viewModel.removeEquation() },
                enabled = numberOfEquations > 2,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Remove Equation")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    val hasError = errorStates.any { it }
                    if (!hasError) {
                        val coeffs = coefficients.take(numberOfEquations * fieldsPerEquation)
                            .map { it.toDoubleOrNull() ?: 0.0 }
                        onSolve(coeffs)
                    } else {
                        Toast.makeText(context, "Please correct the highlighted fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            ) {
                Text("Solve")
            }

            Button(
                onClick = {
                    val hasError = errorStates.any { it }

                    if (!hasError) {
                        // If no errors, proceed to save
                        onSave(coefficients.take(numberOfEquations * fieldsPerEquation).toList())
                        Toast.makeText(context, "Coefficients saved to file", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please correct the highlighted fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp)
            ) {
                Text("Save")
            }

            Button(
                onClick = {
                    viewModel.loadCoefficients(context)
                    Toast.makeText(context, "Coefficients loaded from file", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp)
            ) {
                Text("Load")
            }

            Button(
                onClick = {
                    onClear()
                    Toast.makeText(context, "All data cleared and reset to default", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {
                Text("Clear")
            }
        }
    }
}
