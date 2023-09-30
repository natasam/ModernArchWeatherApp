package com.weathercleanarch.domain.usecase.location

import com.weathercleanarch.data.repository.GeolocationRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val geolocationRepositoryImpl: GeolocationRepositoryImpl) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        geolocationRepositoryImpl.getLastKnownLocation()
    }
}
