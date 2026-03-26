package com.fatupopz.staticfm.data.repository

import com.fatupopz.staticfm.data.api.SpotifyApi
import com.fatupopz.staticfm.data.api.SpotifyTokenManager
import com.fatupopz.staticfm.data.model.*
import kotlinx.coroutines.flow.first

class SpotifyRepository(
    private val api: SpotifyApi = SpotifyApi.create(),
    private val tokenManager: SpotifyTokenManager
) {
    private suspend fun getAuthHeader(): String {
        val token = tokenManager.accessToken.first() ?: ""
        return "Bearer $token"
    }

    suspend fun getCurrentUser(): Result<SpotifyUser> = runCatching {
        api.getCurrentUser(getAuthHeader())
    }

    suspend fun getTopTracks(
        timeRange: String = "medium_term",
        limit: Int = 50
    ): Result<TopTracksResponse> = runCatching {
        api.getTopTracks(getAuthHeader(), timeRange, limit)
    }

    suspend fun getTopArtists(
        timeRange: String = "medium_term",
        limit: Int = 50
    ): Result<TopArtistsResponse> = runCatching {
        api.getTopArtists(getAuthHeader(), timeRange, limit)
    }

    suspend fun getRecentlyPlayed(): Result<RecentlyPlayedResponse> = runCatching {
        api.getRecentlyPlayed(getAuthHeader())
    }

    suspend fun getCurrentlyPlaying(): Result<CurrentlyPlayingResponse?> = runCatching {
        api.getCurrentlyPlaying(getAuthHeader())
    }
}