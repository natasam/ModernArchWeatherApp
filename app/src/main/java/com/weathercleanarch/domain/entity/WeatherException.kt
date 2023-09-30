package com.weathercleanarch.domain.entity

enum class WeatherException(val value: String){
     GPS_DISABLED("GPS Disabled"),
     NO_PERMISSION ("No Permission"),
   NO_INTERNET_CONNECTION ("No Internet Connection"),
    UNKNOWN_ERROR ("Unknown Error")
}