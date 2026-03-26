package com.fatupopz.staticfm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fatupopz.staticfm.data.api.SpotifyTokenManager
import com.fatupopz.staticfm.data.model.TrackItem
import com.fatupopz.staticfm.data.repository.SpotifyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TracksUiState(
    val isLoading: Boolean = true,
    val tracks: List<TrackItem> = emptyList(),
    val selectedTimeRange: String = "medium_term",
    val error: String? = null
)

class TracksViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = SpotifyTokenManager(application)
    private val repository = SpotifyRepository(tokenManager = tokenManager)

    private val _uiState = MutableStateFlow(TracksUiState())
    val uiState: StateFlow<TracksUiState> = _uiState

    init { loadTracks() }

    fun loadTracks(timeRange: String = "medium_term") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, selectedTimeRange = timeRange)
            val result = repository.getTopTracks(timeRange = timeRange, limit = 50)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                tracks    = result.getOrNull()?.items ?: emptyList(),
                error     = result.exceptionOrNull()?.message
            )
        }
    }
}