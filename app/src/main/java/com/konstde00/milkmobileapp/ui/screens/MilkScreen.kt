package com.konstde00.milkmobileapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.konstde00.milkmobileapp.db.DBHelper
import com.konstde00.milkmobileapp.ui.components.MilkItem
import com.konstde00.milkmobileapp.viewmodel.MilkViewModel
import com.konstde00.milkmobileapp.viewmodel.MilkViewModelFactory

@Composable
fun MilkScreen(
    navController: NavController,
    dbHelper: DBHelper,
    viewModel: MilkViewModel = viewModel(factory = MilkViewModelFactory(dbHelper))
) {
    val milks by viewModel.milks.collectAsState(initial = listOf())

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Filter Production > 11,000,000")
            Switch(
                checked = viewModel.isFilterEnabled.collectAsState().value,
                onCheckedChange = { viewModel.setFilter(it) }
            )
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(milks) { milk ->
                MilkItem(
                    milk = milk,
                    onEdit = {
                        navController.navigate("edit/${milk.id}")
                    },
                    onDelete = {
                        viewModel.deleteMilk(milk.id)
                    }
                )
            }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate("add")
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Milk")
        }
    }
}
