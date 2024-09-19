package com.konstde00.lab_1.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CoefficientInput(
    onSolve: (List<Double>) -> Unit,
    onSave: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val coefficients = remember { mutableStateListOf(*Array(6) { "" }) }
    val context = LocalContext.current

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

        // Input fields for coefficients
        for (i in 0 until 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (j in 0 until 3) {
                    TextField(
                        value = coefficients[i * 3 + j],
                        onValueChange = { newValue ->
                            coefficients[i * 3 + j] = newValue
                        },
                        label = { Text("a${i + 1}${j + 1}") },
                        modifier = Modifier
                            .width(80.dp)
                            .padding(end = 8.dp)
                    )
                }
            }
        }

        // "Solve" button
        Button(
            onClick = {
                val coeffs = coefficients.map { it.toDoubleOrNull() ?: 0.0 }
                onSolve(coeffs)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text("Solve")
        }

        // "Save Coefficients" button
        Button(
            onClick = {
                onSave(coefficients.toList())
                Toast.makeText(context, "Coefficients saved to file", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        ) {
            Text("Save Coefficients")
        }
    }
}
