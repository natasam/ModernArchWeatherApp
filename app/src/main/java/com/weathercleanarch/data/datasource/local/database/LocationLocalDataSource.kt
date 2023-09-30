package com.weathercleanarch.data.datasource.local.database

import com.weathercleanarch.data.datasource.local.database.model.LocationCityDbDto
import com.weathercleanarch.data.datasource.local.database.db.LocationCityDao
import javax.inject.Inject

class LocationLocalDataSource @Inject constructor(private val myCityDao: LocationCityDao) {

    suspend fun addMyCity(myCityEntity: LocationCityDbDto) = myCityDao.addCity(myCityEntity)

    fun getMyCity() = myCityDao.getMyCity()

    fun deleteMyCity(cityName: String) = myCityDao.deleteMyCity(cityName)

    suspend fun updateMyCity(
        temp: Double,
        latitude: Double,
        longitude: Double,
        cityName: String,
        description: String,
        weatherImage: String
    ) = myCityDao.updateMyCity(
        temp = temp,
        latitude = latitude,
        longitude = longitude,
        cityName = cityName,
        description = description,
        weatherImageDesc = weatherImage
    )

    suspend fun getSpecificCity(cityName: String) = myCityDao.getSpecificCity(cityName)
}