package com.blissphinehas.wmatatransit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stops")
data class Stop(
    @PrimaryKey val stopId: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    val routes: String, // comma-separated route names
    val isFavorite: Boolean = false
)