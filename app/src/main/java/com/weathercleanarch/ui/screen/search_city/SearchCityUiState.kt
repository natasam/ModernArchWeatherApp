package com.weathercleanarch.ui.screen.search_city

import com.weathercleanarch.domain.entity.Forecast

sealed interface SearchCityUiState {
    data class Success(val forecast: Forecast?): SearchCityUiState
    data class Error(val errorMessage: String?): SearchCityUiState

    object Loading: SearchCityUiState
}