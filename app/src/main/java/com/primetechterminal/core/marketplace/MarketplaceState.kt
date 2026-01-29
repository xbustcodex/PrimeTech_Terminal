package com.primetechterminal.core.marketplace
 
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

private val Context.marketplaceDataStore by preferencesDataStore(name = "marketplace_state")

class MarketplaceState(private val context: Context) {

    private fun installedKey(id: String) = booleanPreferencesKey("installed_$id")
    private fun enabledKey(id: String) = booleanPreferencesKey("enabled_$id")

    fun isInstalledFlow(id: String): Flow<Boolean> =
        context.marketplaceDataStore.data.map { it[installedKey(id)] ?: false }

    fun isEnabledFlow(id: String): Flow<Boolean> =
        context.marketplaceDataStore.data.map { it[enabledKey(id)] ?: false }

    suspend fun setInstalled(id: String, value: Boolean) {
        context.marketplaceDataStore.edit { prefs ->
            prefs[installedKey(id)] = value
            // If you uninstall, also disable
            if (!value) prefs[enabledKey(id)] = false
        }
    }

    suspend fun setEnabled(id: String, value: Boolean) {
        context.marketplaceDataStore.edit { prefs ->
            // Enabling implies installed
            if (value) prefs[installedKey(id)] = true
            prefs[enabledKey(id)] = value
        }
    }
    suspend fun isEnabled(id: String, defaultValue: Boolean = true): Boolean {
        val prefs = context.marketplaceDataStore.data.first()
        return prefs[enabledKey(id)] ?: defaultValue
    } 
    suspend fun isInstalled(id: String, defaultValue: Boolean = false): Boolean {
        val prefs = context.marketplaceDataStore.data.first()
        return prefs[installedKey(id)] ?: defaultValue
    }
}

