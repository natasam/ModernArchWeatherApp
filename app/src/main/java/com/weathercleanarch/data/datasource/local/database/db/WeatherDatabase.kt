package com.weathercleanarch.data.datasource.local.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weathercleanarch.data.datasource.local.database.model.CityDbDto
import com.weathercleanarch.data.datasource.local.database.model.ForecastDbDto
import com.weathercleanarch.data.datasource.local.database.model.LocationCityDbDto

@Database(entities = [CityDbDto::class, ForecastDbDto::class, LocationCityDbDto::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun forecastWeatherDao(): ForecastDao

    abstract fun locationCityDao(): LocationCityDao
}