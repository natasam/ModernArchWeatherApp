package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("country") val country: String,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("name") val cityName: String,
    @SerializedName("coord") val coordinate: CoordResponse
)
