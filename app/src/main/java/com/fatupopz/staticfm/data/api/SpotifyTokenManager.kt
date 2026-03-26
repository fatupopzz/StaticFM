package com.fatupopz.staticfm.data.api

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "spotify_tokens")

object TokenKeys {
    val ACCESS_TOKEN  = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val EXPIRES_AT    = stringPreferencesKey("expires_at")
}

class SpotifyTokenManager(private val context: Context) {

    val accessToken: Flow<String?> = context.dataStore.data
        .map { it[TokenKeys.ACCESS_TOKEN] }

    val refreshToken: Flow<String?> = context.dataStore.data
        .map { it[TokenKeys.REFRESH_TOKEN] }

    suspend fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Int) {
        val expiresAt = System.currentTimeMillis() + (expiresIn * 1000L)
        context.dataStore.edit { prefs ->
            prefs[TokenKeys.ACCESS_TOKEN]  = accessToken
            prefs[TokenKeys.REFRESH_TOKEN] = refreshToken
            prefs[TokenKeys.EXPIRES_AT]    = expiresAt.toString()
        }
    }

    suspend fun clearTokens() {
        context.dataStore.edit { it.clear() }
    }

    suspend fun isTokenExpired(): Boolean {
        var expired = true
        context.dataStore.data.map { prefs ->
            val expiresAt = prefs[TokenKeys.EXPIRES_AT]?.toLongOrNull() ?: 0L
            expired = System.currentTimeMillis() >= expiresAt
        }
        return expired
    }
}