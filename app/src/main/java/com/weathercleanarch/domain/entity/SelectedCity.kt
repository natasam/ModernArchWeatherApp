package com.weathercleanarch.domain.entity


data class SelectedCity(
    var temp: Double,
    var latitude: Double,
    var longitude: Double,
    var cityName: String,
    var country: String,
    var description: String,
    var weatherImageDesc: String
)
