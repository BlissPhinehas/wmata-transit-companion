package com.blissphinehas.wmatatransit.ui.arrivals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blissphinehas.wmatatransit.data.repository.TransitRepository
import com.blissphinehas.wmatatransit.model.Arrival
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ArrivalsUiState(
    val arrivals: List<Arrival> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ArrivalsViewModel(private val repository: TransitRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ArrivalsUiState())
    val uiState: StateFlow<ArrivalsUiState> = _uiState

    fun loadArrivals(stopId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.getArrivals(stopId).fold(
                onSuccess = { arrivals ->
                    _uiState.value = _uiState.value.copy(
                        arrivals = arrivals,
                        isLoading = false
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Failed to load arrivals",
                        isLoading = false
                    )
                }
            )
        }
    }
}