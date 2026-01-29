package com.primetechterminal.core.plugins.impl

import com.primetechterminal.core.commands.CommandResult
import com.primetechterminal.core.plugins.ToolEntry
import com.primetechterminal.core.plugins.Plugin

class AppsPlugin(
    private val getTools: () -> List<ToolEntry>
) : Plugin {
    override val name = "apps"
    override val help = "Lists your APK tools and versions"
    override val aliases = listOf("tools")

    override fun run(args: List<String>): CommandResult {
        val tools = getTools()
        val lines = mutableListOf("Installed / Available tools:")
        tools.forEach {
            lines.add("- ${it.id}: ${it.displayName}  (${it.version ?: "?"})")
        }
        lines.add("Use: open <id>   e.g. open termuxfm")
        return CommandResult(lines)
    }
}
