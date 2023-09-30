package com.weathercleanarch.ui.screen.search_city

import com.weathercleanarch.domain.entity.SelectedCity

sealed interface SavedCitiesUiState {
    data class Success(val forecast: List<SelectedCity>?): SavedCitiesUiState
    data class Error(val errorMessage: String?): SavedCitiesUiState

    object Loading: SavedCitiesUiState
}