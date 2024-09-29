package com.konstde00.lab_1.data

sealed class SolverSolution {

    data class TwoDimensional(
        val matrixSolution: Pair<Double, Double>,
        val gaussSolution: Pair<Double, Double>
    ) : SolverSolution()

    data class ThreeDimensional(
        val matrixSolution: Triple<Double, Double, Double>,
        val gaussSolution: Triple<Double, Double, Double>
    ) : SolverSolution()
}
