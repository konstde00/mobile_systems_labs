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
import com.konstde00.milkmobileapp.viewmodel.MilkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMilkScreen(navController: NavController, milkId: Int, viewModel: MilkViewModel = viewModel()) {
    val milk = viewModel.getMilkById(milkId)

    if (milk == null) {
        Text("No record found", modifier = Modifier.fillMaxSize(), style = MaterialTheme.typography.bodyLarge)
        return
    }

    var year by remember { mutableStateOf(milk.year.toString()) }
    var production by remember { mutableStateOf(milk.production.toString()) }
    var cost by remember { mutableStateOf(milk.cost.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit milk production data") },
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

                        val updatedMilk = milk.copy(
                            year = year.toIntOrNull() ?: milk.year,
                            production = production.toDoubleOrNull() ?: milk.production,
                            cost = cost.toDoubleOrNull() ?: milk.cost
                        )
                        viewModel.updateMilk(updatedMilk)
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save",
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
                    val updatedMilk = milk.copy(
                        year = year.toIntOrNull() ?: milk.year,
                        production = production.toDoubleOrNull() ?: milk.production,
                        cost = cost.toDoubleOrNull() ?: milk.cost
                    )
                    viewModel.updateMilk(updatedMilk)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
