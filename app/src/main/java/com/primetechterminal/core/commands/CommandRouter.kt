package com.primetechterminal.core.commands

import com.primetechterminal.core.plugins.PluginRegistry

class CommandRouter(
    private val registry: PluginRegistry
) {
    fun run(raw: String): CommandResult {
        val parts = raw.trim().split(Regex("\\s+"))
        val cmd = parts.firstOrNull()?.lowercase() ?: return CommandResult(listOf(""))
        val args = parts.drop(1)

        val plugin = registry.findCommand(cmd)
        return plugin?.run(args)
            ?: CommandResult(listOf("Unknown command: $cmd", "Type: help"))
    }
}
