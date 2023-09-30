package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class MainInfoResponse(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Int,
)
