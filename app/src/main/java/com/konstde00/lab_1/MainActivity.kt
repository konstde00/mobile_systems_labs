package com.konstde00.lab_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.konstde00.lab_1.ui.components.AuthorPage
import com.konstde00.lab_1.ui.components.CoefficientInput
import com.konstde00.lab_1.ui.components.EquationGraph
import com.konstde00.lab_1.ui.components.ErrorDialog
import com.konstde00.lab_1.ui.components.SolutionDisplay
import com.konstde00.lab_1.ui.theme.Lab_1Theme
import com.konstde00.lab_1.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab_1Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainPage(navController = navController)
                    }
                    composable("author") {
                        AuthorPage(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

@Composable
fun MainPage(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val context = LocalContext.current
        val results by viewModel.results.collectAsState()
        val times by viewModel.times.collectAsState()
        val coefficientsList by viewModel.coefficientsList.collectAsState()
        val numberOfEquations by viewModel.numberOfEquations.collectAsState()
        val errorMessage by viewModel.errorMessage.collectAsState()

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Button(
                onClick = { navController.navigate("author") },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Text("About Author")
            }

            CoefficientInput(
                onSolve = { coeffs ->
                    viewModel.solveEquations(coeffs)
                },
                onSave = { coeffs ->
                    viewModel.saveCoefficients(context, coeffs)
                },
                onClear = {
                    viewModel.clearAll(context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            results?.let {
                SolutionDisplay(
                    results = it,
                    times = times
                )
                Spacer(modifier = Modifier.height(16.dp))
                EquationGraph(
                    coeffs = coefficientsList,
                    numberOfEquations = numberOfEquations,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        if (errorMessage != null) {
            ErrorDialog(
                message = errorMessage ?: "An unknown error occurred.",
                onDismiss = { viewModel.clearError() }
            )
        }
    }
}
