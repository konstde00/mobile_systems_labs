package com.konstde00.lab_1.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konstde00.lab_1.data.MathSolver
import com.konstde00.lab_1.data.FileUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainViewModel : ViewModel() {

    private val _results = MutableStateFlow<Pair<Pair<Double, Double>, Pair<Double, Double>>?>(null)
    val results: StateFlow<Pair<Pair<Double, Double>, Pair<Double, Double>>?> = _results

    private val _times = MutableStateFlow<Pair<Long, Long>?>(null)
    val times: StateFlow<Pair<Long, Long>?> = _times

    private val _coefficientsList = MutableStateFlow<List<Double>>(emptyList())
    val coefficientsList: StateFlow<List<Double>> = _coefficientsList

    fun solveEquations(coeffs: List<Double>) {
        _coefficientsList.value = coeffs
        try {
            val timeMatrix = measureTimeMillis {
                val solutionMatrix = MathSolver.solveByMatrixMethod(coeffs)
                _results.value = Pair(solutionMatrix, _results.value?.second ?: Pair(0.0, 0.0))
            }

            val timeGauss = measureTimeMillis {
                val solutionGauss = MathSolver.solveByGaussMethod(coeffs)
                _results.value = Pair(_results.value?.first ?: Pair(0.0, 0.0), solutionGauss)
            }

            _times.value = Pair(timeMatrix, timeGauss)
        } catch (e: Exception) {
            // Handle exception, e.g., send an event to show a Toast
        }
    }

    fun saveCoefficients(context: Context, coeffs: List<String>) {
        viewModelScope.launch {
            FileUtils.saveCoefficientsToFile(context, coeffs)
            // Optionally handle success or failure
        }
    }
}
