package com.konstde00.milkmobileapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.konstde00.milkmobileapp.model.MilkProduction
import com.konstde00.milkmobileapp.viewmodel.MilkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMilkScreen(navController: NavController, viewModel: MilkViewModel = viewModel()) {
    var year by remember { mutableStateOf("") }
    var production by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add milk production") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val newMilk = MilkProduction(
                            year = year.toIntOrNull() ?: 0,
                            production = production.toDoubleOrNull() ?: 0.0,
                            cost = cost.toDoubleOrNull() ?: 0.0
                        )
                        viewModel.insertMilk(newMilk)
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = production,
                onValueChange = { production = it },
                label = { Text("Production (t)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("Price ($)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newMilk = MilkProduction(
                        year = year.toIntOrNull() ?: 0,
                        production = production.toDoubleOrNull() ?: 0.0,
                        cost = cost.toDoubleOrNull() ?: 0.0
                    )
                    viewModel.insertMilk(newMilk)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add")
            }
        }
    }
}
