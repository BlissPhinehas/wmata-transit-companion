package com.blissphinehas.wmatatransit.data.db

import androidx.room.*
import com.blissphinehas.wmatatransit.model.Stop
import kotlinx.coroutines.flow.Flow

@Dao
interface StopDao {

    @Query("SELECT * FROM stops WHERE isFavorite = 1")
    fun getFavoriteStops(): Flow<List<Stop>>

    @Query("SELECT * FROM stops WHERE stopId = :stopId")
    suspend fun getStopById(stopId: String): Stop?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStop(stop: Stop)

    @Delete
    suspend fun deleteStop(stop: Stop)

    @Query("UPDATE stops SET isFavorite = :isFavorite WHERE stopId = :stopId")
    suspend fun updateFavorite(stopId: String, isFavorite: Boolean)
}