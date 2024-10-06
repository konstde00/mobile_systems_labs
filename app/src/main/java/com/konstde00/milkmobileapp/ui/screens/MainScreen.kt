package com.konstde00.milkmobileapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.konstde00.milkmobileapp.db.DBHelper
import com.konstde00.milkmobileapp.viewmodel.MilkViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(dbHelper: DBHelper) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("home")
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Milk") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("milk")
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Contacts") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("contacts")
                    }
                )
                NavigationDrawerItem(
                    label = { Text("About author") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("about")
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Milk Mobile App") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            NavHost(navController, startDestination = "home", Modifier.padding(paddingValues)) {
                composable("home") { HomeScreen(navController) }
                composable("milk") {
                    MilkScreen(navController, dbHelper)
                }
                composable("contacts") { ContactsScreen(navController) }
                composable("about") { AuthorPage(navController) }
                composable("edit/{id}") { backStackEntry ->
                    Log.d("MainScreen", "Args: ${backStackEntry.arguments}")
                    val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                    EditMilkScreen(navController = navController, milkId = id, viewModel = MilkViewModel(dbHelper))
                }
                composable("add") { AddMilkScreen(navController = navController, viewModel = MilkViewModel(dbHelper)) }
            }
        }
    }
}
