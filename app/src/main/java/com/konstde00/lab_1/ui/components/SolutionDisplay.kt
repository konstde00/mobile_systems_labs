package com.konstde00.lab_1.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.konstde00.lab_1.data.SolverSolution
import kotlin.math.pow

@Composable
fun SolutionDisplay(
    results: SolverSolution?,
    times: Pair<Long, Long>?
) {
    results?.let { solution ->
        Column(modifier = Modifier.padding(16.dp)) {
            when (solution) {
                is SolverSolution.TwoDimensional -> {
                    Text("Matrix Method Solution:", style = MaterialTheme.typography.titleMedium)
                    Text("x = ${round(solution.matrixSolution.first)}")
                    Text("y = ${round(solution.matrixSolution.second)}")
                    Text("Time taken: ${times?.first ?: "N/A"} ns")

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Gauss Method Solution:", style = MaterialTheme.typography.titleMedium)
                    Text("x = ${round(solution.gaussSolution.first)}")
                    Text("y = ${round(solution.gaussSolution.second)}")
                    Text("Time taken: ${times?.second ?: "N/A"} ns")

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Difference in time: ${times?.let { kotlin.math.abs(it.first - it.second) } ?: "N/A"} ns"
                   )
                }
                is SolverSolution.ThreeDimensional -> {
                    Text("Matrix Method Solution:", style = MaterialTheme.typography.titleMedium)
                    Text("x = ${round(solution.matrixSolution.first)}")
                    Text("y = ${round(solution.matrixSolution.second)}")
                    Text("z = ${round(solution.matrixSolution.third)}")
                    Text("Time taken: ${times?.first ?: "N/A"} ns")

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Gauss Method Solution:", style = MaterialTheme.typography.titleMedium)
                    Text("x = ${round(solution.gaussSolution.first)}")
                    Text("y = ${round(solution.gaussSolution.second)}")
                    Text("z = ${round(solution.gaussSolution.third)}")
                    Text("Time taken: ${times?.second ?: "N/A"} ns")

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Difference in time: ${times?.let { kotlin.math.abs(it.first - it.second) } ?: "N/A"} ns"
                   )
                }
            }
        }
    }
}

private fun round(value: Double, places: Int = 8): Double {
    if (places < 0) throw IllegalArgumentException("Decimal places must be non-negative.")
    val factor = 10.0.pow(places)
    return kotlin.math.round(value * factor) / factor
}
