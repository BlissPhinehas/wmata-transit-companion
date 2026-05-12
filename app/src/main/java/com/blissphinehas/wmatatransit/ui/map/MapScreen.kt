package com.blissphinehas.wmatatransit.ui.map

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.blissphinehas.wmatatransit.model.Stop

@Composable
fun MapScreen(
    viewModel: MapViewModel,
    onStopClick: (Stop) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Default to Washington DC center
    val dcCenter = LatLng(38.9072, -77.0369)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(dcCenter, 13f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                viewModel.loadNearbyStops(dcCenter.latitude, dcCenter.longitude)
            }
        ) {
            uiState.nearbyStops.forEach { stop ->
                Marker(
                    state = MarkerState(position = LatLng(stop.lat, stop.lon)),
                    title = stop.name,
                    snippet = "Routes: ${stop.routes}",
                    onClick = {
                        onStopClick(stop)
                        false
                    }
                )
            }
        }

        // Loading indicator
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
        }

        // Error message
        uiState.error?.let { error ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // Nearby stops list at bottom
        if (uiState.nearbyStops.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .padding(8.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(uiState.nearbyStops) { stop ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    stop.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    "Routes: ${stop.routes}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Row {
                                IconButton(onClick = {
                                    viewModel.saveStopAsFavorite(stop)
                                }) {
                                    Icon(
                                        Icons.Default.Favorite,
                                        contentDescription = "Save favorite"
                                    )
                                }
                                TextButton(onClick = { onStopClick(stop) }) {
                                    Text("Arrivals")
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}