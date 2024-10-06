package com.konstde00.milkmobileapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konstde00.milkmobileapp.repository.MilkRepository

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val repository = remember { MilkRepository(context) }
    val milkData by remember { mutableStateOf(repository.getAll()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Average production: ${repository.getAverageProduction()} t")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Production data", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(milkData.size) { index ->
                val item = milkData[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("details/${item.id}") }
                        .padding(8.dp)
                ) {
                    Text("Year: ${item.year}, amount: ${item.production} t")
                }
            }
        }
    }
}
