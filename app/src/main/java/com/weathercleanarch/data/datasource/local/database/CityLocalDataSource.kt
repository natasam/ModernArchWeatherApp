package com.weathercleanarch.data.datasource.local.database

import com.weathercleanarch.data.datasource.local.database.model.CityDbDto
import com.weathercleanarch.data.datasource.local.database.db.CityDao
import javax.inject.Inject

class CityLocalDataSource @Inject constructor(private val cityDao: CityDao) {

    suspend fun addCity(cityDbDto: CityDbDto) =
        cityDao.addCity(cityDbDto)

    fun getCity() = cityDao.getCity()

    suspend fun updateCity(cityDbDto: CityDbDto) =
        cityDao.updateCity(cityDbDto)
}