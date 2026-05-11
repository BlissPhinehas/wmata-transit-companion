package com.blissphinehas.wmatatransit.data.api

import com.google.gson.annotations.SerializedName

data class BusPredictionsResponse(
    @SerializedName("Predictions") val predictions: List<BusPrediction>
)

data class BusPrediction(
    @SerializedName("RouteID") val routeId: String,
    @SerializedName("DirectionText") val directionText: String,
    @SerializedName("Minutes") val minutes: Int,
    @SerializedName("TripHeadsign") val tripHeadsign: String,
    @SerializedName("StopID") val stopId: String
)

data class BusStopsResponse(
    @SerializedName("Stops") val stops: List<BusStop>
)

data class BusStop(
    @SerializedName("StopID") val stopId: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Lat") val lat: Double,
    @SerializedName("Lon") val lon: Double,
    @SerializedName("Routes") val routes: List<String>
)