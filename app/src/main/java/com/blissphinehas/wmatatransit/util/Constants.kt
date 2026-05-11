package com.blissphinehas.wmatatransit.util

import com.blissphinehas.wmatatransit.BuildConfig

object Constants {
    const val WMATA_BASE_URL = "https://api.wmata.com/"
    val WMATA_API_KEY: String = BuildConfig.WMATA_API_KEY
    const val NOTIFICATION_CHANNEL_ID = "bus_alerts"
    const val NOTIFICATION_CHANNEL_NAME = "Bus Alerts"
    const val ARRIVAL_ALERT_MINUTES = 5
}