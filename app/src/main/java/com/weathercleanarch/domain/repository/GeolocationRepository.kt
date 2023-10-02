package com.weathercleanarch.domain.repository

import com.weathercleanarch.domain.entity.Geolocation
import com.weathercleanarch.domain.entity.Result
import kotlinx.coroutines.flow.Flow


interface GeolocationRepository {
    suspend fun getLastKnownLocation(): Flow<Result<Geolocation>>
}