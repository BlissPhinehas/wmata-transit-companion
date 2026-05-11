package com.blissphinehas.wmatatransit.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blissphinehas.wmatatransit.data.repository.TransitRepository
import com.blissphinehas.wmatatransit.model.Stop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MapUiState(
    val nearbyStops: List<Stop> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class MapViewModel(private val repository: TransitRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState

    fun loadNearbyStops(lat: Double, lon: Double) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.getNearbyStops(lat, lon).fold(
                onSuccess = { stops ->
                    _uiState.value = _uiState.value.copy(
                        nearbyStops = stops,
                        isLoading = false
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Failed to load stops",
                        isLoading = false
                    )
                }
            )
        }
    }

    fun saveStopAsFavorite(stop: Stop) {
        viewModelScope.launch {
            repository.saveFavoriteStop(stop)
        }
    }
}