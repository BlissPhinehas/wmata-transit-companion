package com.blissphinehas.wmatatransit.data.repository

import com.blissphinehas.wmatatransit.data.api.RetrofitClient
import com.blissphinehas.wmatatransit.data.db.AppDatabase
import com.blissphinehas.wmatatransit.model.Arrival
import com.blissphinehas.wmatatransit.model.Stop
import com.blissphinehas.wmatatransit.util.Constants
import kotlinx.coroutines.flow.Flow

class TransitRepository(private val db: AppDatabase) {

    private val api = RetrofitClient.wmataApiService

    // --- Remote ---

    suspend fun getArrivals(stopId: String): Result<List<Arrival>> {
        return try {
            val response = api.getBusArrivals(Constants.WMATA_API_KEY, stopId)
            val arrivals = response.predictions.map {
                Arrival(
                    routeId = it.routeId,
                    directionText = it.directionText,
                    minutes = it.minutes.toString(),
                    tripHeadsign = it.tripHeadsign ?: it.directionText,
                    stopId = stopId
                )
            }
            Result.success(arrivals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNearbyStops(lat: Double, lon: Double): Result<List<Stop>> {
        return try {
            val response = api.getNearbyStops(Constants.WMATA_API_KEY, lat, lon)
            val stops = response.stops.map {
                Stop(
                    stopId = it.stopId,
                    name = it.name,
                    lat = it.lat,
                    lon = it.lon,
                    routes = it.routes.joinToString(",")
                )
            }
            Result.success(stops)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Local ---

    fun getFavoriteStops(): Flow<List<Stop>> {
        return db.stopDao().getFavoriteStops()
    }

    suspend fun saveFavoriteStop(stop: Stop) {
        db.stopDao().insertStop(stop.copy(isFavorite = true))
    }

    suspend fun removeFavoriteStop(stop: Stop) {
        db.stopDao().deleteStop(stop)
    }

    suspend fun toggleFavorite(stopId: String, isFavorite: Boolean) {
        db.stopDao().updateFavorite(stopId, isFavorite)
    }
}