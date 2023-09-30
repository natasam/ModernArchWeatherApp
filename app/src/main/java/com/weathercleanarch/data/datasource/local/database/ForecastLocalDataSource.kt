package com.weathercleanarch.data.datasource.local.database

import com.weathercleanarch.data.datasource.local.database.model.CityDbDto
import com.weathercleanarch.data.datasource.local.database.model.ForecastDbDto
import com.weathercleanarch.data.datasource.local.database.db.CityDao
import com.weathercleanarch.data.datasource.local.database.db.ForecastDao
import javax.inject.Inject

class ForecastLocalDataSource @Inject constructor(
    private val forecastDao: ForecastDao,
    private val cityDao: CityDao
) {

    suspend fun addForecastWeather(forecastEntity: ForecastDbDto) =
        forecastDao.addForecastWeather(forecastEntity)

    suspend fun addCity(cityDbDto: CityDbDto) =
        cityDao.addCity(cityDbDto)

    fun getForecastWeather() = forecastDao.getForecastWeather()
    fun getCity() = cityDao.getCity()

    suspend fun updateForecastWeather(forecastDbDto: ForecastDbDto) =
        forecastDao.updateForecastWeather(forecastDbDto)

    suspend fun updateCity(cityDbDto: CityDbDto) =
        cityDao.updateCity(cityDbDto)
}