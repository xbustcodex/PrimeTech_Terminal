package com.primetechterminal.ui.terminal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.primetechterminal.core.marketplace.MarketplaceRepository
import com.primetechterminal.core.marketplace.MarketplaceState
import com.primetechterminal.core.marketplace.MarketplaceTool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MarketplacePanel(
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // ✅ GitHub RAW JSON URL
    val feedUrl = "https://raw.githubusercontent.com/xbustcodex/PrimeTech_Terminal/main/marketplace.json"
    val repo = remember { MarketplaceRepository(feedUrl) }
    val state = remember { MarketplaceState(context) }

    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var tools by remember { mutableStateOf<List<MarketplaceTool>>(emptyList()) }

    // ✅ makes Retry actually reload
    var reloadTick by remember { mutableStateOf(0) }

    LaunchedEffect(reloadTick) {
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
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Marketplace",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Monospace
            )
            OutlinedButton(onClick = onClose) { Text("Close") }
        }

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
                Button(onClick = { reloadTick++ }) { Text("Retry") }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tools, key = { it.id }) { tool ->
                        val installed by state.isInstalledFlow(tool.id).collectAsState(initial = false)
                        val enabled by state.isEnabledFlow(tool.id).collectAsState(initial = false)

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

                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    if (!installed) {
                                        Button(onClick = {
                                            // ✅ Step 1: mark installed (we'll add real APK download next)
                                            scope.launch { state.setInstalled(tool.id, true) }
                                        }) { Text("Install") }
                                    } else {
                                        OutlinedButton(onClick = {
                                            scope.launch { state.setInstalled(tool.id, false) }
                                        }) { Text("Uninstall") }

                                        FilledTonalButton(onClick = {
                                            scope.launch { state.setEnabled(tool.id, !enabled) }
                                        }) { Text(if (enabled) "Disable" else "Enable") }
                                    }
                                }

                                if (installed && tool.apkUrl.isNotBlank()) {
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        "APK: ${tool.apkUrl}",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
