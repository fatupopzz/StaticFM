
package com.fatupopz.staticfm.data.model

data class SpotifyUser(
    val id: String,
    val display_name: String?,
    val email: String?,
    val images: List<SpotifyImage>,
    val followers: Followers?
) {
    val displayName get() = display_name ?: id
    val avatarUrl get() = images.firstOrNull()?.url ?: ""
}