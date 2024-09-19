package com.konstde00.lab_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konstde00.lab_1.ui.components.CoefficientInput
import com.konstde00.lab_1.ui.components.EquationGraph
import com.konstde00.lab_1.ui.components.SolutionDisplay
import com.konstde00.lab_1.ui.theme.Lab_1Theme
import com.konstde00.lab_1.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab_1Theme {
                val viewModel: MainViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    val results by viewModel.results.collectAsState()
                    val times by viewModel.times.collectAsState()
                    val coefficientsList by viewModel.coefficientsList.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        CoefficientInput(
                            onSolve = { coeffs ->
                                viewModel.solveEquations(coeffs)
                            },
                            onSave = { coeffs ->
                                viewModel.saveCoefficients(context, coeffs)
                            },
                            modifier = Modifier.weight(1f)
                        )

                        results?.let {
                            SolutionDisplay(results = it, times = times!!)
                            EquationGraph(coeffs = coefficientsList)
                        }
                    }
                }
            }
        }
    }
}