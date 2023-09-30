package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class WindResponse(
    @SerializedName("speed") val speed: Double,
)
