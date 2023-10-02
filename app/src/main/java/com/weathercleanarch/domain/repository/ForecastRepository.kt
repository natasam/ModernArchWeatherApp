package com.weathercleanarch.domain.repository

import com.weathercleanarch.domain.entity.City
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.Result

interface ForecastRepository {
    suspend fun getForecastData(latitude: Double, longitude: Double): Result<Forecast>
    suspend fun getForecastDataWithCityName(cityName: String): Result<Forecast>
    suspend fun addForecastWeather(forecast: Forecast)
    suspend fun addCity(city: City)
    fun getForecastWeather(): Forecast?
    fun getCity(): City
    suspend fun updateForecastWeather(forecast: Forecast)
    suspend fun updateCity(city: City)
}