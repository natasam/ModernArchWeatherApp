package com.weathercleanarch.domain.repository

import com.weathercleanarch.domain.entity.City
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.Resource

interface ForecastRepository {
    suspend fun getForecastData(latitude: Double, longitude: Double): Resource<Forecast>
    suspend fun getForecastDataWithCityName(cityName: String): Resource<Forecast>
    suspend fun addForecastWeather(forecast: Forecast)
    suspend fun addCity(city: City)
    fun getForecastWeather() : Forecast?
    fun getCity() : City
    suspend fun updateForecastWeather(forecast: Forecast)
    suspend fun updateCity(city: City)
}