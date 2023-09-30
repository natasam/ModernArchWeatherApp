package com.weathercleanarch.data.datasource.remote

import com.weathercleanarch.data.mapper.toForecastDto
import com.weathercleanarch.domain.entity.Forecast
import javax.inject.Inject

class ForecastRemoteDataSource @Inject constructor(private val api: WeatherApi) {
    suspend fun getForecastByLocation(latitude: Double, longitude: Double): Forecast =
        api.getForecastByLocation(latitude, longitude).toForecastDto()

    suspend fun getForecastByCityName(cityName: String) =
        api.getForecastByCityName(cityName).toForecastDto()
}