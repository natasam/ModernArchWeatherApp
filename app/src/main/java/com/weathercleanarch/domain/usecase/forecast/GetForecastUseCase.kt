package com.weathercleanarch.domain.usecase.forecast

import com.weathercleanarch.data.repository.ForecastRepositoryImpl
import com.weathercleanarch.data.repository.GeolocationRepositoryImpl
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val forecastRepositoryImpl: ForecastRepositoryImpl,
    private val geolocationRepositoryImpl: GeolocationRepositoryImpl
) {
    suspend operator fun invoke(): Result<Forecast> = withContext(
        Dispatchers.Default
    ) {
        val geolocation = geolocationRepositoryImpl
            .getLastKnownLocation().first().data
        forecastRepositoryImpl.getForecastData(geolocation!!.latitude, geolocation.longitude)
    }
}