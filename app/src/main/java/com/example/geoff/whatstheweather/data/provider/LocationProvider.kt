package com.example.geoff.whatstheweather.data.provider

interface LocationProvider {
    suspend fun getDeviceLocationString(): String
}