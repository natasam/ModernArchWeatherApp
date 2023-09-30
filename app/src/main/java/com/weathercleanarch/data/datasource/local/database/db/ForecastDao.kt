package com.weathercleanarch.data.datasource.local.database.db

import androidx.room.*
import com.weathercleanarch.data.datasource.local.database.model.ForecastDbDto

@Dao
interface ForecastDao {
    @Insert
    suspend fun addForecastWeather(forecastDbDto: ForecastDbDto)

    @Query("SELECT * FROM forecast_table")
    fun getForecastWeather(): List<ForecastDbDto>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateForecastWeather(forecastDbDto: ForecastDbDto)
}