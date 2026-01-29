package com.primetechterminal.ui.terminal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.primetechterminal.core.marketplace.MarketplaceRepository
import com.primetechterminal.core.marketplace.MarketplaceTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MarketplacePanel(
    onClose: () -> Unit
) {
    // ✅ Put your GitHub RAW JSON URL here:
    val feedUrl = "https://raw.githubusercontent.com/xbustcodex/PrimeTech_Terminal/main/marketplace.json"
    val repo = remember { MarketplaceRepository(feedUrl) }

    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var tools by remember { mutableStateOf<List<MarketplaceTool>>(emptyList()) }

    LaunchedEffect(Unit) {
        isLoading = true
        error = null
        try {
            val feed = withContext(Dispatchers.IO) { repo.fetch() }
            tools = feed.tools
        } catch (e: Exception) {
            error = e.message ?: "Failed to load marketplace"
        } finally {
            isLoading = false
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Marketplace",
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily.Monospace
        )

        Spacer(Modifier.height(12.dp))

        when {
            isLoading -> {
                LinearProgressIndicator(Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                Text("Loading tools…", fontFamily = FontFamily.Monospace)
            }
            error != null -> {
                Text("Error: $error", fontFamily = FontFamily.Monospace)
                Spacer(Modifier.height(10.dp))
                Button(onClick = {
                    // quick reload
                    isLoading = true
                    error = null
                    tools = emptyList()
                }) { Text("Retry") }
            }
            else -> {
                LazyColumn(Modifier.weight(1f)) {
                    items(tools) { tool ->
                        Card(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(12.dp)) {
                                Text(tool.name, fontFamily = FontFamily.Monospace)
                                if (tool.version.isNotBlank()) {
                                    Text("v${tool.version}", fontFamily = FontFamily.Monospace)
                                }
                                if (tool.description.isNotBlank()) {
                                    Spacer(Modifier.height(6.dp))
                                    Text(tool.description)
                                }

                                Spacer(Modifier.height(10.dp))

                                Row {
                                    Button(
                                        onClick = {
                                            // Next step: download + install
                                            // tool.apkUrl is what we will use
                                        }
                                    ) { Text("Install") }

                                    Spacer(Modifier.width(10.dp))

                                    OutlinedButton(onClick = onClose) { Text("Close") }
                                }
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))
        OutlinedButton(onClick = onClose, modifier = Modifier.fillMaxWidth()) {
            Text("Close")
        }
    }
}
