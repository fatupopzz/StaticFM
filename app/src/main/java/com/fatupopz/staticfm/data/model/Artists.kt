package com.fatupopz.staticfm.data.model

data class Artist(
    val id: String,
    val name: String,
    val genres: List<String>,
    val images: List<SpotifyImage>,
    val popularity: Int,
    val followers: Followers?
) {
    val imageUrl get() = images.firstOrNull()?.url ?: ""
    val followersCount get() = followers?.total ?: 0
}

data class Followers(
    val total: Int
)