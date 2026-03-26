package com.fatupopz.staticfm.data.model

data class Track(
    val id: String,
    val name: String,
    val artists: List<Artist>,
    val album: Album,
    val durationMs: Int,
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

data class Album(
    val id: String,
    val name: String,
    val images: List<SpotifyImage>
)

data class SpotifyImage(
    val url: String,
    val width: Int?,
    val height: Int?
)