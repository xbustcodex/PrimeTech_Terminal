package com.primetechterminal.core.plugins.impl

import com.primetechterminal.core.commands.CommandResult
import com.primetechterminal.core.plugins.Plugin

class AboutPlugin : Plugin {
    override val name = "about"
    override val help = "Shows PrimeTech links + info"
    override val aliases = listOf("info")

    override fun run(args: List<String>): CommandResult {
        return CommandResult(
            listOf(
                "PrimeTech Terminal",
                "--------------------------------",
                "Ko-fi: (add your link here)",
                "Gumroad: (add your link here)",
                "GitHub: (add your link here)",
                "--------------------------------",
                "Type: help"
            )
        )
    }
}
