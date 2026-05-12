package com.blissphinehas.wmatatransit.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blissphinehas.wmatatransit.data.db.AppDatabase
import com.blissphinehas.wmatatransit.data.repository.TransitRepository
import com.blissphinehas.wmatatransit.util.Constants
import com.blissphinehas.wmatatransit.util.NotificationHelper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class BusAlertService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var repository: TransitRepository

    override fun onCreate() {
        super.onCreate()
        val db = AppDatabase.getInstance(applicationContext)
        repository = TransitRepository(db)
        NotificationHelper.createNotificationChannel(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val stopId = intent?.getStringExtra("stopId") ?: return START_NOT_STICKY
        val stopName = intent.getStringExtra("stopName") ?: return START_NOT_STICKY

        serviceScope.launch {
            checkArrivals(stopId, stopName)
        }

        return START_NOT_STICKY
    }

    private suspend fun checkArrivals(stopId: String, stopName: String) {
        repository.getArrivals(stopId).onSuccess { arrivals ->
            arrivals
                .filter { it.minutes.toIntOrNull() != null }
                .filter { it.minutes.toInt() <= Constants.ARRIVAL_ALERT_MINUTES }
                .forEach { arrival ->
                    NotificationHelper.sendBusAlert(
                        applicationContext,
                        arrival.routeId,
                        stopName,
                        arrival.minutes.toInt()
                    )
                }
        }
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}