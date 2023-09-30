package com.weathercleanarch.domain.repository

import com.weathercleanarch.domain.entity.Geolocation
import com.weathercleanarch.domain.entity.Resource
import kotlinx.coroutines.flow.Flow


interface GeolocationRepository {
    suspend fun getLastKnownLocation(): Flow<Resource<Geolocation>>
}