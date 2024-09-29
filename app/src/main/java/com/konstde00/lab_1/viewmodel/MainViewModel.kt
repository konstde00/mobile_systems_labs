package com.konstde00.lab_1.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konstde00.lab_1.data.FileUtils
import com.konstde00.lab_1.data.EquationSolver
import com.konstde00.lab_1.data.SolverSolution
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureNanoTime

class MainViewModel : ViewModel() {

    private val _results = MutableStateFlow<SolverSolution?>(null)
    val results: StateFlow<SolverSolution?> = _results

    private val _times = MutableStateFlow<Pair<Long, Long>?>(null)
    val times: StateFlow<Pair<Long, Long>?> = _times

    private val _coefficientsList = MutableStateFlow<List<Double>>(emptyList())
    val coefficientsList: StateFlow<List<Double>> = _coefficientsList

    private val _numberOfEquations = MutableStateFlow(2)
    val numberOfEquations: StateFlow<Int> = _numberOfEquations

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun addEquation() {
        if (_numberOfEquations.value < 3) {
            _numberOfEquations.value += 1
        }
    }

    fun removeEquation() {
        if (_numberOfEquations.value > 2) {
            _numberOfEquations.value -= 1
            val updatedCoeffs = _coefficientsList.value.toMutableList()
            if (updatedCoeffs.size >= 4) {
                repeat(4) {
                    updatedCoeffs.removeLastOrNull()
                }
                _coefficientsList.value = updatedCoeffs
            }
        }
    }

    fun solveEquations(coeffs: List<Double>) {
        _coefficientsList.value = coeffs
        try {
            lateinit var matrixSolution: Any
            lateinit var gaussSolution: Any

            val timeMatrix = measureNanoTime {
                matrixSolution = EquationSolver.solveByMatrixMethod(coeffs, _numberOfEquations.value)
            }

            val timeGauss = measureNanoTime {
                gaussSolution = EquationSolver.solveByGaussMethod(coeffs, _numberOfEquations.value)
            }

            _results.value = when (_numberOfEquations.value) {
                2 -> {
                    if (matrixSolution !is Pair<*, *> || gaussSolution !is Pair<*, *>) {
                        throw Exception("Invalid solution type for 2 equations.")
                    }
                    SolverSolution.TwoDimensional(
                        matrixSolution = matrixSolution as Pair<Double, Double>,
                        gaussSolution = gaussSolution as Pair<Double, Double>
                    )
                }
                3 -> {
                    if (matrixSolution !is Triple<*, *, *> || gaussSolution !is Triple<*, *, *>) {
                        throw Exception("Invalid solution type for 3 equations.")
                    }
                    SolverSolution.ThreeDimensional(
                        matrixSolution = matrixSolution as Triple<Double, Double, Double>,
                        gaussSolution = gaussSolution as Triple<Double, Double, Double>
                    )
                }
                else -> throw Exception("Unsupported number of equations: ${_numberOfEquations.value}")
            }

            _times.value = Pair(timeMatrix, timeGauss)
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error solving equations: ${e.message}", e)
            _errorMessage.value = e.message
        }
    }

    fun saveCoefficients(context: Context, coeffs: List<String>) {
        viewModelScope.launch {
            FileUtils.saveCoefficientsToFile(context, coeffs)
        }
    }

    fun loadCoefficients(context: Context) {
        viewModelScope.launch {
            val loadedCoeffs = FileUtils.loadCoefficientsFromFile(context)
            if (loadedCoeffs != null && loadedCoeffs.isNotEmpty()) {
                val parsedCoeffs = loadedCoeffs.map { it.toDoubleOrNull() ?: 0.0 }
                _coefficientsList.value = parsedCoeffs

                val loadedNumberOfEquations = when (parsedCoeffs.size) {
                    6 -> 2   // 2 equations with 3 coefficients each (x, y, d)
                    12 -> 3  // 3 equations with 4 coefficients each (x, y, z, d)
                    else -> 0 // Invalid number of coefficients
                }

                if (loadedNumberOfEquations in 2..3) {
                    _numberOfEquations.value = loadedNumberOfEquations
                } else {
                    Log.e("MainViewModel", "Invalid number of coefficients loaded: ${parsedCoeffs.size}")
                    _numberOfEquations.value = 2

                    _coefficientsList.value = parsedCoeffs.take(6) + List(6) { 0.0 }
                }
            } else {
                Log.e("MainViewModel", "No coefficients loaded from the file.")
            }
        }
    }

    fun clearAll(context: Context) {
        viewModelScope.launch {
            _coefficientsList.value = emptyList()
            _numberOfEquations.value = 2
            _results.value = null
            _times.value = null
            _errorMessage.value = null
            FileUtils.clearCoefficientsFile(context)
            Log.d("MainViewModel", "All data cleared and state reset to default.")
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
