package com.blissphinehas.wmatatransit.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blissphinehas.wmatatransit.data.repository.TransitRepository
import com.blissphinehas.wmatatransit.ui.arrivals.ArrivalsViewModel
import com.blissphinehas.wmatatransit.ui.favorites.FavoritesViewModel
import com.blissphinehas.wmatatransit.ui.map.MapViewModel

class ViewModelFactory(private val repository: TransitRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ArrivalsViewModel::class.java) ->
                ArrivalsViewModel(repository) as T
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(repository) as T
            modelClass.isAssignableFrom(MapViewModel::class.java) ->
                MapViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}