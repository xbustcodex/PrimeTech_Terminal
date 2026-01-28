package com.primetechterminal.ui.terminal

import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import com.primetechterminal.core.plugins.DefaultPluginRegistry


@Composable
fun TerminalApp() {
    val ctx = LocalContext.current
    val registry = remember { DefaultPluginRegistry(ctx) }

    var showLeftPanel by remember { mutableStateOf(false) }
    var showRightPanel by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {

        // === FULLSCREEN TERMINAL (base layer) ===
        TerminalScreen(
            modifier = Modifier.fillMaxSize(),
            registry = registry
        )

        // === TOP BAR BUTTONS ===
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()              // pushes below system status bar
                .padding(
                    top = 8.dp,                  // 👈 extra breathing room
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 4.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Top-left button
            IconButton(
                onClick = { showLeftPanel = !showLeftPanel },
                modifier = Modifier.padding(40.dp) // Add padding for easy touch target
            ) {
                Text("☰", style = MaterialTheme.typography.titleLarge)
            }

            // Top-right button
            IconButton(
                onClick = { showRightPanel = !showRightPanel },
                modifier = Modifier.padding(40.dp) // Add padding for easy touch target
            ) {
                Text("🛒", style = MaterialTheme.typography.titleLarge)
            }
        }

        // --- dim background (scrim) when any panel open ---
        AnimatedVisibility(
            visible = showLeftPanel || showRightPanel,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            // Clicking outside closes whichever panel is open
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable {
                        showLeftPanel = false
                        showRightPanel = false
                    }
            )
        }

        // --- LEFT SLIDE PANEL ---
        AnimatedVisibility(
            visible = showLeftPanel,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            Surface(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight(),
                tonalElevation = 8.dp
            ) {
                LeftPanel(onClose = { showLeftPanel = false })
            }
        }

        // --- RIGHT SLIDE PANEL ---
        AnimatedVisibility(
            visible = showRightPanel,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(320.dp)
                    .fillMaxHeight(),
                tonalElevation = 8.dp
            ) {
                MarketplacePanel(onClose = { showRightPanel = false })
            }
        }
    }
}
