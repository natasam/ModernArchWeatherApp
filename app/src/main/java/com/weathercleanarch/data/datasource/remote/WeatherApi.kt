package com.weathercleanarch.data.datasource.remote

import com.weathercleanarch.data.datasource.remote.response.ForecastResponse
import com.weathercleanarch.data.datasource.NetworkService
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(NetworkService.FORECAST_END_POINT)
    suspend fun getForecastByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") apiKey: String = NetworkService.API_KEY,
        @Query("units") units: String = NetworkService.UNITS,
    ): ForecastResponse

    @GET(NetworkService.FORECAST_END_POINT)
    suspend fun getForecastByCityName(
        @Query("q") cityName: String,
        @Query("APPID") apiKey: String = NetworkService.API_KEY,
        @Query("units") units: String = NetworkService.UNITS,
    ): ForecastResponse
}