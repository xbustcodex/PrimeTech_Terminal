package com.primetechterminal.core.plugins

import com.primetechterminal.core.commands.CommandResult

interface Plugin {
    val name: String
    val help: String
    val aliases: List<String>

    fun run(args: List<String>): CommandResult
}
