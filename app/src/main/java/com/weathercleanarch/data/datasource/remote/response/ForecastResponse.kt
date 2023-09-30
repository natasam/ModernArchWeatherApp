package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val weatherList: List<ForecastWeatherResponse>,
    @SerializedName("city") val city: CityResponse
)
