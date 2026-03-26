package com.fatupopz.staticfm.data.api

import com.fatupopz.staticfm.data.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApi {

    @GET("me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): SpotifyUser

    @GET("me/top/tracks")
    suspend fun getTopTracks(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String = "medium_term",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): TopTracksResponse

    @GET("me/top/artists")
    suspend fun getTopArtists(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String = "medium_term",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): TopArtistsResponse

    @GET("me/player/recently-played")
    suspend fun getRecentlyPlayed(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 50
    ): RecentlyPlayedResponse

    @GET("me/player/currently-playing")
    suspend fun getCurrentlyPlaying(
        @Header("Authorization") token: String
    ): CurrentlyPlayingResponse?

    companion object {
        private const val BASE_URL = "https://api.spotify.com/v1/"

        fun create(): SpotifyApi {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SpotifyApi::class.java)
        }
    }
}

// Time ranges de Spotify
enum class TimeRange(val value: String, val labelRes: Int) {
    SHORT_TERM("short_term", 0),   // ~4 semanas
    MEDIUM_TERM("medium_term", 0), // ~6 meses
    LONG_TERM("long_term", 0)      // todo el tiempo
}