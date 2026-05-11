package com.blissphinehas.wmatatransit.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blissphinehas.wmatatransit.data.repository.TransitRepository
import com.blissphinehas.wmatatransit.model.Stop
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: TransitRepository) : ViewModel() {

    val favoriteStops: StateFlow<List<Stop>> = repository
        .getFavoriteStops()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(stop: Stop) {
        viewModelScope.launch {
            repository.removeFavoriteStop(stop)
        }
    }
}