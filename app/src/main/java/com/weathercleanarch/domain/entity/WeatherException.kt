package com.weathercleanarch.domain.entity

sealed class WeatherException(val value: String) {
    object NoPermissionError : WeatherException("No Location Permission permission")
    object NoLocationError : WeatherException("No User Location")
    object NoInternetConnectionError : WeatherException("No Internet Connection")
    object UnknownError : WeatherException("Unknown Error")
    data class UnknownErrorWithMsg(val msg: String?) : WeatherException("Unknown Error")

}