package com.konstde00.lab_1.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SolutionDisplay(results: Pair<Pair<Double, Double>, Pair<Double, Double>>, times: Pair<Long, Long>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Matrix Method Solution:", style = MaterialTheme.typography.titleMedium)
        Text("x = ${results.first.first}")
        Text("y = ${results.first.second}")
        Text("Time taken: ${times.first} ms")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Gauss Method Solution:", style = MaterialTheme.typography.titleMedium)
        Text("x = ${results.second.first}")
        Text("y = ${results.second.second}")
        Text("Time taken: ${times.second} ms")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Difference in time: ${kotlin.math.abs(times.first - times.second)} ms")
    }
}
