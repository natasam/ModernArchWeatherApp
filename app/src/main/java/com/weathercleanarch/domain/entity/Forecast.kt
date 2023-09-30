package com.weathercleanarch.domain.entity

data class Forecast(
    val weatherList: List<ForecastWeather>,
    val city: City
)
