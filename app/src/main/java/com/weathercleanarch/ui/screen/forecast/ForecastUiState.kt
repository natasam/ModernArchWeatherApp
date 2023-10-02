package com.weathercleanarch.ui.screen.forecast

import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.WeatherException

sealed interface ForecastUiState {
    data class Success(val forecast: Forecast?) : ForecastUiState
    data class Error(val exception: WeatherException?, val msg: String? = null) : ForecastUiState
    object Loading : ForecastUiState
}