package com.primetechterminal.ui.terminal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import com.primetechterminal.core.commands.CommandRouter
import com.primetechterminal.core.commands.TerminalLine
import com.primetechterminal.core.plugins.PluginRegistry

@Composable
fun TerminalScreen(
    modifier: Modifier,
    registry: PluginRegistry
) {
    val router = remember { CommandRouter(registry) }

    var prompt by remember { mutableStateOf("PrimeTech>") }
    var input by remember { mutableStateOf("") }
    val lines = remember { mutableStateListOf<TerminalLine>() }

    Surface(modifier = modifier) {
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()        // ✅ pushes top down
                .navigationBarsPadding()    // ✅ pulls bottom up
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {

            // Optional header (can remove later for pure Termux feel)
            Text(
                text = "PrimeTech Terminal",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.Monospace
            )

            Spacer(Modifier.height(8.dp))

            // === OUTPUT ===
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(lines) { line ->
                    Text(
                        text = line.text,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // === INPUT ROW ===
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$prompt ",
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = input,
                    onValueChange = { input = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            "type a command…",
                            fontFamily = FontFamily.Monospace
                        )
                    }
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = {
                        val cmd = input.trim()
                        if (cmd.isNotEmpty()) {
                            lines.add(TerminalLine("$prompt $cmd"))
                            input = ""

                            val result = router.run(cmd)
                            result.lines.forEach {
                                lines.add(TerminalLine(it))
                            }

                            result.newPrompt?.let { prompt = it }
                        }
                    }
                ) {
                    Text("Run")
                }
            }
        }
    }
}
