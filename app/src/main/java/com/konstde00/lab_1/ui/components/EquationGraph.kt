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
fun EquationGraph(
    coeffs: List<Double>,
    numberOfEquations: Int,
    modifier: Modifier = Modifier
) {
    if (coeffs.size < numberOfEquations * 3) return

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height

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

        val arrowSize = 10f

        drawLine(
            color = Color.Black,
            start = Offset(width, height / 2),
            end = Offset(width - arrowSize, height / 2 - arrowSize)
        )
        drawLine(
            color = Color.Black,
            start = Offset(width, height / 2),
            end = Offset(width - arrowSize, height / 2 + arrowSize)
        )

        drawLine(
            color = Color.Black,
            start = Offset(width / 2, 0f),
            end = Offset(width / 2 - arrowSize, arrowSize)
        )
        drawLine(
            color = Color.Black,
            start = Offset(width / 2, 0f),
            end = Offset(width / 2 + arrowSize, arrowSize)
        )

        val scaleX = width / 20f
        val scaleY = height / 20f

        val xRange = -10..10
        val yRange = -10..10

        val labelPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 24f
            textAlign = android.graphics.Paint.Align.CENTER
        }

        for (i in xRange) {
            val xPixel = width / 2 + i * scaleX

            if (xPixel >= 0 && xPixel <= width) {
                drawLine(
                    color = Color.Black,
                    start = Offset(xPixel, height / 2 - 5),
                    end = Offset(xPixel, height / 2 + 5)
                )
                if (i != 0) {
                    drawContext.canvas.nativeCanvas.drawText(
                        i.toString(),
                        xPixel,
                        height / 2 + 20,
                        labelPaint
                    )
                }
            }
        }

        labelPaint.textAlign = android.graphics.Paint.Align.RIGHT

        for (i in yRange) {
            val yPixel = height / 2 - i * scaleY

            if (yPixel >= 0 && yPixel <= height) {
                drawLine(
                    color = Color.Black,
                    start = Offset(width / 2 - 5, yPixel),
                    end = Offset(width / 2 + 5, yPixel)
                )
                if (i != 0) {
                    drawContext.canvas.nativeCanvas.drawText(
                        i.toString(),
                        width / 2 - 10,
                        yPixel + 8,
                        labelPaint
                    )
                }
            }
        }

        val equations = mutableListOf<Triple<Double, Double, Double>>()
        for (i in 0 until numberOfEquations) {
            val base = i * 3
            equations.add(Triple(coeffs[base], coeffs[base + 1], coeffs[base + 2]))
        }

        val colors = listOf(Color.Red, Color.Blue, Color.Green)

        for ((index, equation) in equations.withIndex()) {
            val path = Path()
            val (a, b, c) = equation

            var firstPointSet = false

            for (xPixel in 0..width.toInt()) {
                val x = (xPixel - width / 2) / scaleX
                if (b == 0.0) continue
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
                color = colors.getOrNull(index) ?: Color.Black,
                style = Stroke(width = 2f)
            )
        }
    }
}
