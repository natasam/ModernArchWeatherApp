package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(
    @SerializedName("main") val mainWeatherInfo: MainInfoResponse,
    @SerializedName("weather") val weatherStatus: List<WeatherResponse>,
    @SerializedName("wind") val wind: WindResponse,
    @SerializedName("dt_txt") val date: String,
    @SerializedName("clouds") val cloudinessDto: CloudsResponse
)
