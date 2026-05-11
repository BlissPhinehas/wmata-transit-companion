package com.blissphinehas.wmatatransit.ui.arrivals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blissphinehas.wmatatransit.model.Arrival

@Composable
fun ArrivalsScreen(
    stopId: String,
    stopName: String,
    viewModel: ArrivalsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(stopId) {
        viewModel.loadArrivals(stopId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = stopName,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            uiState.arrivals.isEmpty() -> {
                Text("No arrivals found for this stop.")
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.arrivals) { arrival ->
                        ArrivalCard(arrival)
                    }
                }
            }
        }
    }
}

@Composable
fun ArrivalCard(arrival: Arrival) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = arrival.routeId,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = arrival.tripHeadsign,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = when (arrival.minutes) {
                    "0" -> "ARR"
                    else -> "${arrival.minutes} min"
                },
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}