package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class CoordResponse(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)
