package com.primetechterminal.core.marketplace

import android.content.Context
import kotlinx.serialization.json.Json

class MarketplaceRepository(
    private val context: Context,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    fun loadLocalMarketplace(): MarketplaceIndex {
        val text = context.assets.open("marketplace.json").bufferedReader().use { it.readText() }
        return json.decodeFromString(MarketplaceIndex.serializer(), text)
    }
}
