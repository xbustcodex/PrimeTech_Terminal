package com.primetechterminal.core.marketplace

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarketplaceIndex(
    val version: Int = 1,
    val plugins: List<MarketplacePlugin> = emptyList()
)

@Serializable
data class MarketplacePlugin(
    val id: String,
    val name: String,
    val description: String = "",
    val author: String = "",
    val version: String = "1.0.0",

    // builtin | remote (future)
    val type: String = "builtin",

    // Fully qualified class name for plugin entry (for builtin plugins)
    @SerialName("entryPoint")
    val entryPoint: String = "",

    val commands: List<String> = emptyList()
)
