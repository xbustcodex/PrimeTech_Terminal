package com.primetechterminal.core.marketplace

import kotlinx.serialization.Serializable

@Serializable
data class MarketplaceFeed(
    val tools: List<MarketplaceTool>
)

@Serializable
data class MarketplaceTool(
    val id: String,
    val name: String,
    val description: String = "",
    val version: String = "",
    val apkUrl: String = "",
    val repoUrl: String = ""
)
