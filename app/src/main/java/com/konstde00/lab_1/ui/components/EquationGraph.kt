package com.konstde00.lab_1.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun EquationGraph(coeffs: List<Double>, modifier: Modifier = Modifier) {
    if (coeffs.size < 6) return

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height

        // Draw axes
        drawLine(
            color = Color.Black,
            start = Offset(0f, height / 2),
            end = Offset(width, height / 2)
        )
        drawLine(
            color = Color.Black,
            start = Offset(width / 2, 0f),
            end = Offset(width / 2, height)
        )

        val scaleX = width / 20f  // assuming x ranges from -10 to 10
        val scaleY = height / 20f // assuming y ranges from -10 to 10

        // Draw the equations
        val equations = listOf(
            Triple(coeffs[0], coeffs[1], coeffs[2]),
            Triple(coeffs[3], coeffs[4], coeffs[5])
        )

        val colors = listOf(Color.Red, Color.Blue)

        for ((index, equation) in equations.withIndex()) {
            val path = Path()
            val (a, b, c) = equation

            var firstPointSet = false

            for (xPixel in 0..width.toInt()) {
                val x = (xPixel - width / 2) / scaleX
                val y = (c - a * x) / b

                val yPixel = height / 2 - y * scaleY

                if (yPixel in 0f..height) {
                    if (!firstPointSet) {
                        path.moveTo(xPixel.toFloat(), yPixel.toFloat())
                        firstPointSet = true
                    } else {
                        path.lineTo(xPixel.toFloat(), yPixel.toFloat())
                    }
                }
            }

            drawPath(
                path = path,
                color = colors[index],
                style = Stroke(width = 2f)
            )
        }
    }
}
