// package com.konstde00.milkmobileapp.ui.screens

package com.konstde00.milkmobileapp.ui.screens

import android.Manifest
import android.net.Uri
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.konstde00.milkmobileapp.data.models.Contact
import com.konstde00.milkmobileapp.viewmodel.ContactsViewModel

@Composable
fun ContactsScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ContactsViewModel = viewModel()
    val contacts by viewModel.contacts.collectAsState()

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasPermission = isGranted
            if (isGranted) {
                viewModel.loadContacts(context.contentResolver)
            }
        }
    )

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        } else {
            viewModel.loadContacts(context.contentResolver)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Contacts", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        when {
            !hasPermission -> {
                Text(
                    text = "Permission to read contacts is required to display contacts.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            contacts.isEmpty() -> {
                Text(
                    text = "No contacts with name starts with 'Ми' found.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> {
                LazyColumn {
                    items(contacts) { contact ->
                        ContactItem(contact = contact) {
                            val encodedAddress = Uri.encode(contact.address)
                            navController.navigate("map/$encodedAddress")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${contact.name}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Number: ${contact.number}", style = MaterialTheme.typography.bodyMedium)
            if (contact.address.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Address: ${contact.address}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
