package com.weathercleanarch.data.datasource.local.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.weathercleanarch.data.datasource.local.database.model.LocationCityDbDto
import com.weathercleanarch.data.datasource.Database

@Dao
interface LocationCityDao {
    @Insert
    suspend fun addCity(myCityEntity: LocationCityDbDto)

    @Query("SELECT * FROM ${Database.location_city_table}")
    fun getMyCity(): List<LocationCityDbDto>

    @Query("DELETE FROM ${Database.location_city_table} WHERE city = :cityName")
    fun deleteMyCity(cityName: String)

    @Query("UPDATE ${Database.location_city_table} SET `temp` = :temp, latitude = :latitude, longitude = :longitude, description = :description, weather_image_desc = :weatherImageDesc WHERE city = :cityName")
    suspend fun updateMyCity(
        temp: Double,
        latitude: Double,
        longitude: Double,
        cityName: String,
        description: String,
        weatherImageDesc: String
    )

    @Query("SELECT * FROM ${Database.location_city_table} WHERE city = :cityName")
    suspend fun getSpecificCity(cityName: String) : LocationCityDbDto?
}