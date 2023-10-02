package com.weathercleanarch.domain.usecase.forecast

import com.weathercleanarch.data.repository.ForecastRepositoryImpl
import com.weathercleanarch.domain.entity.Forecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetForecastFromDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend operator fun invoke(): Forecast? = withContext(Dispatchers.IO) {
        forecastRepositoryImpl.getForecastWeather()
    }
}