package com.weathercleanarch.domain.entity

data class ForecastWeather(
    val id: Int = 1,
    val weatherData: Main,
    val weatherStatus: List<Weather>,
    val wind: Wind,
    val date: String,
    val cloudiness: Cloudiness
)
