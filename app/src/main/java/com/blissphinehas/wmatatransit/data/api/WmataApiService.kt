package com.blissphinehas.wmatatransit.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WmataApiService {

    @GET("NextBusService.svc/json/jPredictions")
    suspend fun getBusArrivals(
        @Query("api_key") apiKey: String,
        @Query("StopID") stopId: String
    ): BusPredictionsResponse

    @GET("Bus.svc/json/jStops")
    suspend fun getNearbyStops(
        @Query("api_key") apiKey: String,
        @Query("Lat") lat: Double,
        @Query("Lon") lon: Double,
        @Query("Radius") radius: Int = 500
    ): BusStopsResponse
}