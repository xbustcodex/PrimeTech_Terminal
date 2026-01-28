package com.primetechterminal.ui.terminal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun LeftPanel(
    onClose: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "PrimeTech",
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily.Monospace
        )

        Spacer(Modifier.height(20.dp))

        Button(onClick = { /* coming soon */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Tools (Coming Soon)")
        }

        Spacer(Modifier.height(10.dp))

        Button(onClick = { /* coming soon */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Plugins")
        }

        Spacer(Modifier.height(10.dp))

        Button(onClick = { /* show about */ }, modifier = Modifier.fillMaxWidth()) {
            Text("About")
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Close")
        }
    }
}
