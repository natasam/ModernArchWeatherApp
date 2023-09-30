package com.weathercleanarch.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class CloudsResponse(
    @SerializedName("all") val cloudiness: Int
)