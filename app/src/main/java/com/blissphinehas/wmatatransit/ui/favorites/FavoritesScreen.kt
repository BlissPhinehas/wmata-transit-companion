package com.blissphinehas.wmatatransit.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blissphinehas.wmatatransit.model.Stop

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onStopClick: (Stop) -> Unit
) {
    val stops by viewModel.favoriteStops.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Favorite Stops", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        if (stops.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No favorites yet. Add stops from the map!")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(stops) { stop ->
                    FavoriteStopCard(
                        stop = stop,
                        onClick = { onStopClick(stop) },
                        onDelete = { viewModel.removeFavorite(stop) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteStopCard(
    stop: Stop,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(stop.name, style = MaterialTheme.typography.titleMedium)
                Text("Routes: ${stop.routes}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Remove favorite")
            }
        }
    }
}