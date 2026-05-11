package com.blissphinehas.wmatatransit.model

data class Arrival(
    val routeId: String,
    val directionText: String,
    val minutes: String, // "5", "BRD", "ARR"
    val tripHeadsign: String,
    val stopId: String
)