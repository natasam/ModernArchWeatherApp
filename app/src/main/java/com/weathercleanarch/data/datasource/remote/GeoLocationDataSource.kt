package com.weathercleanarch.data.datasource.remote

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.weathercleanarch.domain.entity.Geolocation
import com.weathercleanarch.domain.entity.Result
import com.weathercleanarch.domain.entity.WeatherException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GeoLocationDataSource @Inject constructor(
    private val locationClient: FusedLocationProviderClient, private val application: Application
) {
    suspend fun getLastKnownLocation() = callbackFlow {
        if (checkGeolocationPermission().not()) {
            trySend(WeatherException.NoPermissionError)
        }
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                val location = result.lastLocation ?: result.locations.lastOrNull()
                if (location == null) {
                    trySend(WeatherException.NoLocationError)
                } else {
                    trySend(
                        Result.Success(
                            Geolocation(
                                latitude = location.latitude, longitude = location.longitude
                            )
                        )
                    )
                }
            }
        }
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000
        ).apply {
            setGranularity(Granularity.GRANULARITY_FINE)
        }.build()
        locationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
        awaitClose {
            locationClient.removeLocationUpdates(locationCallback)
        }
    }

    private fun checkGeolocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}