package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main") val mainDescription: String,
    @SerializedName("description") val description: String
)
