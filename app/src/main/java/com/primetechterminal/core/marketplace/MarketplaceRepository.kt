package com.primetechterminal.core.marketplace

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MarketplaceRepository(
    private val feedUrl: String
) {
    private val client = OkHttpClient()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Throws(IOException::class)
    fun fetch(): MarketplaceFeed {
        val req = Request.Builder().url(feedUrl).build()
        client.newCall(req).execute().use { res ->
            if (!res.isSuccessful) throw IOException("HTTP ${res.code}")
            val body = res.body?.string() ?: throw IOException("Empty body")
            return json.decodeFromString(MarketplaceFeed.serializer(), body)
        }
    }
}
