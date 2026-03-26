package com.fatupopz.staticfm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fatupopz.staticfm.data.api.SpotifyTokenManager
import com.fatupopz.staticfm.data.model.*
import com.fatupopz.staticfm.data.repository.SpotifyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val user: SpotifyUser? = null,
    val topTracks: List<TrackItem> = emptyList(),
    val currentlyPlaying: CurrentlyPlayingResponse? = null,
    val error: String? = null
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = SpotifyTokenManager(application)
    private val repository = SpotifyRepository(tokenManager = tokenManager)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState(isLoading = true)
            try {
                // Cargar en paralelo
                val userResult    = repository.getCurrentUser()
                val tracksResult  = repository.getTopTracks(limit = 4)
                val playingResult = repository.getCurrentlyPlaying()

                _uiState.value = HomeUiState(
                    isLoading       = false,
                    user            = userResult.getOrNull(),
                    topTracks       = tracksResult.getOrNull()?.items ?: emptyList(),
                    currentlyPlaying = playingResult.getOrNull(),
                    error           = userResult.exceptionOrNull()?.message
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState(
                    isLoading = false,
                    error     = e.message
                )
            }
        }
    }
}