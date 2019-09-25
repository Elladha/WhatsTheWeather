package com.example.geoff.whatstheweather.data.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.geoff.whatstheweather.LocationNotGrantedException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

const val DEVICE_LOCATION = "DEVICE_LOCATION"
const val DEFAULT_LOCATION = "Paris"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
    ) : PreferenceProvider(context), LocationProvider {

    private val appContext: Context = context.applicationContext


    override suspend fun getDeviceLocationString(): String {
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocationAsync().await()
                    ?: return DEFAULT_LOCATION
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            } catch (e: LocationNotGrantedException) {
                return DEFAULT_LOCATION
            }
        } else {
            return DEFAULT_LOCATION
        }
    }

    private fun getLastDeviceLocationAsync() : Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationNotGrantedException()
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(DEVICE_LOCATION, true)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }


    private fun <T> Task<T>.asDeferred(): Deferred<T> {
        val deferred = CompletableDeferred<T>()

        this.addOnSuccessListener {
            deferred.complete(it)
        }
        this.addOnFailureListener {
            deferred.completeExceptionally(it)
        }
        return deferred
    }

}