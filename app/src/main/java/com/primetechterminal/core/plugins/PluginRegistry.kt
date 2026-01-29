package com.primetechterminal.core.plugins

interface PluginRegistry {
    fun findCommand(command: String): Plugin?
    fun listTools(): List<ToolEntry>
}
