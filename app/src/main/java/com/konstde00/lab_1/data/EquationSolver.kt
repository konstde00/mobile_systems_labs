package com.konstde00.lab_1.data

object EquationSolver {

    fun solveByMatrixMethod(coeffs: List<Double>, numberOfEquations: Int): Any {
        return when (numberOfEquations) {
            2 -> solve2x2(coeffs)
            3 -> solve3x3(coeffs)
            else -> throw Exception("Unsupported number of equations: $numberOfEquations")
        }
    }

    private fun solve2x2(coeffs: List<Double>): Pair<Double, Double> {
        if (coeffs.size != 6) throw Exception("Invalid number of coefficients for 2 equations.")

        val a11 = coeffs[0]
        val a12 = coeffs[1]
        val d1 = coeffs[2]
        val a21 = coeffs[3]
        val a22 = coeffs[4]
        val d2 = coeffs[5]

        val determinant = a11 * a22 - a12 * a21
        if (determinant == 0.0) throw Exception("No unique solution exists.")

        val x = (d1 * a22 - a12 * d2) / determinant
        val y = (a11 * d2 - a21 * d1) / determinant

        return Pair(x, y)
    }

    private fun solve3x3(coeffs: List<Double>): Triple<Double, Double, Double> {
        if (coeffs.size != 12) throw Exception("Invalid number of coefficients for 3 equations.")

        val a11 = coeffs[0]
        val a12 = coeffs[1]
        val a13 = coeffs[2]
        val d1 = coeffs[3]
        val a21 = coeffs[4]
        val a22 = coeffs[5]
        val a23 = coeffs[6]
        val d2 = coeffs[7]
        val a31 = coeffs[8]
        val a32 = coeffs[9]
        val a33 = coeffs[10]
        val d3 = coeffs[11]

        val matrixA = arrayOf(
            doubleArrayOf(a11, a12, a13),
            doubleArrayOf(a21, a22, a23),
            doubleArrayOf(a31, a32, a33)
        )

        val detA = det3x3(matrixA)

        val epsilon = 1e-10
        if (Math.abs(detA) < epsilon) throw Exception("No unique solution exists.")

        val matrixAx = arrayOf(
            doubleArrayOf(d1, a12, a13),
            doubleArrayOf(d2, a22, a23),
            doubleArrayOf(d3, a32, a33)
        )
        val detAx = det3x3(matrixAx)

        val matrixAy = arrayOf(
            doubleArrayOf(a11, d1, a13),
            doubleArrayOf(a21, d2, a23),
            doubleArrayOf(a31, d3, a33)
        )
        val detAy = det3x3(matrixAy)

        val matrixAz = arrayOf(
            doubleArrayOf(a11, a12, d1),
            doubleArrayOf(a21, a22, d2),
            doubleArrayOf(a31, a32, d3)
        )
        val detAz = det3x3(matrixAz)

        val x = detAx / detA
        val y = detAy / detA
        val z = detAz / detA

        return Triple(x, y, z)
    }

    private fun det3x3(matrix: Array<DoubleArray>): Double {
        return matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) -
                matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]) +
                matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0])
    }

    fun solveByGaussMethod(coeffs: List<Double>, numberOfEquations: Int): Any {
        return when (numberOfEquations) {
            2 -> solveGauss2x2(coeffs)
            3 -> solveGauss3x3(coeffs)
            else -> throw Exception("Unsupported number of equations: $numberOfEquations")
        }
    }

    private fun solveGauss2x2(coeffs: List<Double>): Pair<Double, Double> {
        if (coeffs.size != 6) throw Exception("Invalid number of coefficients for 2 equations.")

        val a11 = coeffs[0]
        val a12 = coeffs[1]
        val d1 = coeffs[2]
        val a21 = coeffs[3]
        val a22 = coeffs[4]
        val d2 = coeffs[5]

        // Forward Elimination
        val factor = a21 / a11
        val newA22 = a22 - factor * a12
        val newD2 = d2 - factor * d1

        if (newA22 == 0.0) throw Exception("No unique solution exists.")

        // Back Substitution
        val y = newD2 / newA22
        val x = (d1 - a12 * y) / a11

        return Pair(x, y)
    }

    private fun solveGauss3x3(coeffs: List<Double>): Triple<Double, Double, Double> {
        if (coeffs.size != 12) throw Exception("Invalid number of coefficients for 3 equations.")

        val a11 = coeffs[0]
        val a12 = coeffs[1]
        val a13 = coeffs[2]
        val d1 = coeffs[3]
        val a21 = coeffs[4]
        val a22 = coeffs[5]
        val a23 = coeffs[6]
        val d2 = coeffs[7]
        val a31 = coeffs[8]
        val a32 = coeffs[9]
        val a33 = coeffs[10]
        val d3 = coeffs[11]

        val matrix = arrayOf(
            doubleArrayOf(a11, a12, a13, d1),
            doubleArrayOf(a21, a22, a23, d2),
            doubleArrayOf(a31, a32, a33, d3)
        )

        // Forward Elimination
        for (i in 0 until 3) {
            // Pivot selection
            var maxRow = i
            for (k in (i + 1) until 3) {
                if (kotlin.math.abs(matrix[k][i]) > kotlin.math.abs(matrix[maxRow][i])) {
                    maxRow = k
                }
            }

            // Swap maximum row with current row
            val temp = matrix[i]
            matrix[i] = matrix[maxRow]
            matrix[maxRow] = temp

            if (matrix[i][i] == 0.0) throw Exception("No unique solution exists.")

            for (k in (i + 1) until 3) {
                val c = matrix[k][i] / matrix[i][i]
                for (j in i until 4) {
                    matrix[k][j] -= c * matrix[i][j]
                }
            }
        }

        // Back Substitution
        val z = matrix[2][3] / matrix[2][2]
        val y = (matrix[1][3] - matrix[1][2] * z) / matrix[1][1]
        val x = (matrix[0][3] - matrix[0][2] * z - matrix[0][1] * y) / matrix[0][0]

        return Triple(x, y, z)
    }
}
