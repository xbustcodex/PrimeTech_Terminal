package com.primetechterminal.core.commands

data class CommandResult(
    val lines: List<String>,
    val newPrompt: String? = null
)
