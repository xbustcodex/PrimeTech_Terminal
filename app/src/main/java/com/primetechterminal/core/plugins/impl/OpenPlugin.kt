package com.primetechterminal.core.plugins.impl

import android.content.Context
import android.content.Intent
import com.primetechterminal.core.commands.CommandResult
import com.primetechterminal.core.plugins.Plugin
import com.primetechterminal.core.plugins.ToolEntry

class OpenPlugin(
    private val ctx: Context,
    private val getTools: () -> List<ToolEntry>
) : Plugin {
    override val name = "open"
    override val help = "Opens one of your apps by id (open termuxfm)"
    override val aliases: List<String> = emptyList()


    override fun run(args: List<String>): CommandResult {
        val id = args.firstOrNull()?.lowercase()
            ?: return CommandResult(listOf("Usage: open <id>", "Example: open termuxfm"))

        val tool = getTools().firstOrNull { it.id == id }
            ?: return CommandResult(listOf("Unknown tool id: $id", "Try: apps"))

        val pkg = tool.packageName
            ?: return CommandResult(listOf("No packageName set for ${tool.id} in ToolRegistry"))

        val launchIntent: Intent? = ctx.packageManager.getLaunchIntentForPackage(pkg)
        return if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(launchIntent)
            CommandResult(listOf("Launching: ${tool.displayName}"))
        } else {
            CommandResult(listOf("Not installed (or no launcher activity): $pkg", "Later we’ll add: install ${tool.id}"))
        }
    }
}
