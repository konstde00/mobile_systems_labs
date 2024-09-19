package com.konstde00.lab_1.data

object MathSolver {

    fun solveByMatrixMethod(coeffs: List<Double>): Pair<Double, Double> {
        val a11 = coeffs[0]
        val a12 = coeffs[1]
        val a13 = coeffs[2]
        val a21 = coeffs[3]
        val a22 = coeffs[4]
        val a23 = coeffs[5]

        val determinant = a11 * a22 - a12 * a21
        if (determinant == 0.0) throw Exception("No unique solution exists.")

        val x = (a13 * a22 - a12 * a23) / determinant
        val y = (a11 * a23 - a13 * a21) / determinant

        return Pair(x, y)
    }

    fun solveByGaussMethod(coeffs: List<Double>): Pair<Double, Double> {
        val matrix = arrayOf(
            doubleArrayOf(coeffs[0], coeffs[1], coeffs[2]),
            doubleArrayOf(coeffs[3], coeffs[4], coeffs[5])
        )

        // Forward Elimination
        val factor = matrix[1][0] / matrix[0][0]
        for (i in 0 until 3) {
            matrix[1][i] -= factor * matrix[0][i]
        }

        // Back Substitution
        val y = matrix[1][2] / matrix[1][1]
        val x = (matrix[0][2] - matrix[0][1] * y) / matrix[0][0]

        return Pair(x, y)
    }
}
