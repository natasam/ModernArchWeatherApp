package com.weathercleanarch.data.repository

import com.weathercleanarch.data.datasource.remote.GeoLocationDataSource
import com.weathercleanarch.domain.entity.Geolocation
import com.weathercleanarch.domain.entity.Result
import com.weathercleanarch.domain.repository.GeolocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeolocationRepositoryImpl
@Inject constructor(
    private val geolocationDataSource: GeoLocationDataSource

) : GeolocationRepository {
    override
    suspend fun getLastKnownLocation(): Flow<Result<Geolocation>> =
        withContext(Dispatchers.Default) {

            geolocationDataSource.getLastKnownLocation()
        } as Flow<Result<Geolocation>>
}