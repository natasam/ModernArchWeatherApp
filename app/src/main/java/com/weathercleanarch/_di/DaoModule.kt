package com.weathercleanarch._di

import com.weathercleanarch.data.datasource.local.database.db.CityDao
import com.weathercleanarch.data.datasource.local.database.db.ForecastDao
import com.weathercleanarch.data.datasource.local.database.db.LocationCityDao
import com.weathercleanarch.data.datasource.local.database.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun bindCurrentWeatherDao(weatherDatabase: WeatherDatabase): CityDao =
        weatherDatabase.cityDao()

    @Provides
    @Singleton
    fun bindForecastDao(weatherDatabase: WeatherDatabase): ForecastDao =
        weatherDatabase.forecastWeatherDao()

    @Provides
    @Singleton
    fun bindMyCityDao(weatherDatabase: WeatherDatabase): LocationCityDao =
        weatherDatabase.locationCityDao()
}