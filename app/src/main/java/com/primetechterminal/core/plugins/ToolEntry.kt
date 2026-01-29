package com.primetechterminal.core.plugins

data class ToolEntry(
    val id: String,
    val displayName: String,
    val packageName: String? = null,
    val version: String? = null
)
