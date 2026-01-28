package com.primetechterminal.core.plugins.impl

import com.primetechterminal.core.commands.CommandResult
import com.primetechterminal.core.plugins.Plugin

class HelpPlugin(
    private val getAll: () -> List<Plugin>
) : Plugin {
    override val name = "help"
    override val help = "Shows available commands"
    override val aliases = listOf("?")

    override fun run(args: List<String>): CommandResult {
        val lines = mutableListOf(
            "PrimeTech Terminal commands:",
            "--------------------------------"
        )

        getAll().forEach {
            lines.add("${it.name}  -  ${it.help}")
        }

        lines.add("--------------------------------")
        lines.add("Tip: try `apps` or `open termuxfm`")
        return CommandResult(lines)
    }
}
