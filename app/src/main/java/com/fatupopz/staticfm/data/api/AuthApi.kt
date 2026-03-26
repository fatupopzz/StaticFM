package com.fatupopz.staticfm.data.api

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.fatupopz.staticfm.BuildConfig

object SpotifyAuth {


    const val CLIENT_ID = BuildConfig.SPOTIFY_CLIENT_ID
    const val REDIRECT_URI = "com.fatupopz.staticfm://callback"

    private const val AUTH_URL = "https://accounts.spotify.com/authorize"

    // Permisos que necesitamos
    private val SCOPES = listOf(
        "user-top-read",           // Top tracks y artistas
        "user-read-recently-played", // Historial reciente
        "user-read-currently-playing", // Now playing
        "user-read-playback-state",  // Estado del player
        "user-read-private",         // Info del usuario
        "user-read-email"            // Email del usuario
    ).joinToString(" ")

    fun buildAuthUrl(): String {
        return Uri.parse(AUTH_URL).buildUpon()
            .appendQueryParameter("client_id", CLIENT_ID)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("scope", SCOPES)
            .appendQueryParameter("show_dialog", "true")
            .build()
            .toString()
    }

    fun openAuthInBrowser(context: Context) {
        val authUrl = buildAuthUrl()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, Uri.parse(authUrl))
    }
}