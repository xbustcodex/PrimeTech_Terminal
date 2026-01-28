package com.primetechterminal.core.plugins

import android.content.Context
import com.primetechterminal.core.plugins.impl.AboutPlugin
import com.primetechterminal.core.plugins.impl.AppsPlugin
import com.primetechterminal.core.plugins.impl.HelpPlugin
import com.primetechterminal.core.plugins.impl.OpenPlugin

class DefaultPluginRegistry(ctx: Context) : PluginRegistry {

    private val tools = listOf(
        ToolEntry(id = "termuxfm", displayName = "Termux File Manager", packageName = "com.termuxfm", version = "3.x"),
        ToolEntry(id = "guardian", displayName = "Guardian", packageName = "com.guardianprime", version = "1.x"),
        ToolEntry(id = "buster", displayName = "Buster", packageName = "com.busterpro", version = "2.x"),
    )

    private val plugins: List<Plugin> = listOf(
        HelpPlugin { allCommands() },
        AppsPlugin { tools },
        OpenPlugin(ctx, { tools }),
        AboutPlugin()
        // install/update plugins come next (we’ll add after this runs)
    )

    private fun allCommands(): List<Plugin> = plugins

    override fun findCommand(command: String): Plugin? {
        val c = command.lowercase()
        return plugins.firstOrNull { it.name == c || it.aliases.contains(c) }
    }

    override fun listTools(): List<ToolEntry> = tools
}
