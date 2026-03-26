package com.fatupopz.staticfm.data.model

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    val items: List<TrackItem>,
    val total: Int,
    val limit: Int,
    val offset: Int
)

data class TrackItem(
    val id: String,
    val name: String,
    val artists: List<ArtistSimple>,
    val album: AlbumItem,
    @SerializedName("duration_ms") val durationMs: Int,
    val popularity: Int
) {
    val artistNames get() = artists.joinToString(", ") { it.name }
    val albumArtUrl get() = album.images.firstOrNull()?.url ?: ""
    val durationFormatted get(): String {
        val minutes = durationMs / 1000 / 60
        val seconds = (durationMs / 1000) % 60
        return "$minutes:${seconds.toString().padStart(2, '0')}"
    }
}

data class ArtistSimple(
    val id: String,
    val name: String
)

data class AlbumItem(
    val id: String,
    val name: String,
    val images: List<ImageItem>
)

data class ImageItem(
    val url: String,
    val width: Int?,
    val height: Int?
)

data class TopArtistsResponse(
    val items: List<ArtistItem>,
    val total: Int,
    val limit: Int,
    val offset: Int
)

data class ArtistItem(
    val id: String,
    val name: String,
    val genres: List<String>,
    val images: List<ImageItem>,
    val popularity: Int,
    val followers: FollowersItem?
) {
    val imageUrl get() = images.firstOrNull()?.url ?: ""
    val followersFormatted get(): String {
        val total = followers?.total ?: 0
        return when {
            total >= 1_000_000 -> "${total / 1_000_000}M"
            total >= 1_000 -> "${total / 1_000}K"
            else -> total.toString()
        }
    }
}

data class FollowersItem(val total: Int)

data class RecentlyPlayedResponse(
    val items: List<RecentlyPlayedItem>
)

data class RecentlyPlayedItem(
    val track: TrackItem,
    @SerializedName("played_at") val playedAt: String
)

data class CurrentlyPlayingResponse(
    val item: TrackItem?,
    @SerializedName("is_playing") val isPlaying: Boolean,
    @SerializedName("progress_ms") val progressMs: Int?
)