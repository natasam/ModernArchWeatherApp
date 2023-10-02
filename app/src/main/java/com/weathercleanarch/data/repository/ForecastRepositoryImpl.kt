package com.weathercleanarch.data.repository

import com.weathercleanarch.data.datasource.local.database.ForecastLocalDataSource
import com.weathercleanarch.data.datasource.remote.ForecastRemoteDataSource
import com.weathercleanarch.data.mapper.SearchedCityEntityMapper.toCityDbDto
import com.weathercleanarch.data.mapper.SearchedCityEntityMapper.toCityEntity
import com.weathercleanarch.data.mapper.mapFromDbDtoList
import com.weathercleanarch.data.mapper.mapToDbEntity

import com.weathercleanarch.domain.entity.City
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.Result
import com.weathercleanarch.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
    private val forecastLocalDataSource: ForecastLocalDataSource,
) : ForecastRepository {
    override suspend fun getForecastData(
        latitude: Double,
        longitude: Double
    ): Result<Forecast> {
        return try {
            Result.Success(
                forecastRemoteDataSource.getForecastByLocation(
                    latitude,
                    longitude
                )
            )
        } catch (e: Exception) {
            Result.Error(e.message ?: "UNKNOWN ERROR")
        }
    }

    override suspend fun getForecastDataWithCityName(cityName: String): Result<Forecast> {
        return try {
            Result.Success(
                forecastRemoteDataSource.getForecastByCityName(cityName)

            )
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "UNKNOWN ERROR")
        }
    }

    override suspend fun addForecastWeather(forecast: Forecast) =
        forecastLocalDataSource.addForecastWeather(
            forecast.mapToDbEntity()
        )

    override suspend fun addCity(city: City) {
        forecastLocalDataSource.addCity(
            city.toCityDbDto()
        )
    }

    override fun getForecastWeather(): Forecast? {
        return if (forecastLocalDataSource.getForecastWeather().isEmpty()) {
            null
        } else {
            mapFromDbDtoList(
                forecastLocalDataSource.getForecastWeather(),
                forecastLocalDataSource.getCity()
            )
        }
    }

    override fun getCity(): City = forecastLocalDataSource.getCity().toCityEntity()


    override suspend fun updateForecastWeather(forecast: Forecast) =
        forecastLocalDataSource.updateForecastWeather(
            forecast.mapToDbEntity()
        )


    override suspend fun updateCity(city: City) = forecastLocalDataSource.updateCity(
        city.toCityDbDto()
    )

}