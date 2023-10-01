package com.weathercleanarch.ui.screen.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weathercleanarch.domain.entity.City
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.Resource
import com.weathercleanarch.domain.entity.WeatherException
import com.weathercleanarch.domain.usecase.forecast.AddCityToDbUseCase
import com.weathercleanarch.domain.usecase.forecast.AddForecastToDbUseCase
import com.weathercleanarch.domain.usecase.forecast.GetForecastFromDbUseCase
import com.weathercleanarch.domain.usecase.forecast.GetForecastUseCase
import com.weathercleanarch.domain.usecase.forecast.UpdateCityDbUseCase
import com.weathercleanarch.domain.usecase.forecast.UpdateForecastDbUseCase
import com.weathercleanarch.domain.usecase.location.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val addForecastToDbUseCase: AddForecastToDbUseCase,
    private val addCityToDbUseCas: AddCityToDbUseCase,
    private val updateCityDbUseCase: UpdateCityDbUseCase,
    private val getForecastFromDbUseCase: GetForecastFromDbUseCase,
    private val updateForecastDbUseCase: UpdateForecastDbUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private val _homeScreenState =
        MutableStateFlow<ForecastUiState>(ForecastUiState.Loading)
    val homeForecastState = _homeScreenState.asStateFlow()

    init {
        loadLocation()
    }

    private fun loadLocation() {
        _homeScreenState.value = ForecastUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getLocationUseCase.invoke()
                fetchForecast()
            } catch (e: Exception) {
                if (isForecastCached()) {
                    getCachedForecast()
                } else {
                    _homeScreenState.value =
                        ForecastUiState.Error(WeatherException.UnknownError, msg = e.message)
                }
            }
        }
    }

    private suspend fun fetchForecast() {
        when (val result = getForecastUseCase.invoke()) {
            is Resource.Success -> {
                _homeScreenState.value = ForecastUiState.Success(result.data)
                if (result.data != null) {
                    if (!isForecastCached()) {
                        cacheForecast(result.data, result.data.city)
                    } else {
                        updateCachedForecast(result.data, result.data.city)
                    }
                }
            }

            is Resource.Error -> {
                _homeScreenState.value =
                    ForecastUiState.Error(WeatherException.UnknownError, result.message)
            }
        }
    }

    private suspend fun cacheForecast(forecast: Forecast, city: City) {
        addForecastToDbUseCase.invoke(
            forecast,
            forecast.weatherList.size
        )
        addCityToDbUseCas.invoke(city)
    }

    private suspend fun updateCachedForecast(forecast: Forecast, city: City) {
        updateForecastDbUseCase.invoke(
            forecast,
            forecast.weatherList.size
        )
        updateCityDbUseCase.invoke(city)
    }

    // Data cannot be null.
    // Because before this function is called, it is checked for null with the isForecastCached() function.
    private suspend fun getCachedForecast() {
        _homeScreenState.value =
            ForecastUiState.Success(getForecastFromDbUseCase.invoke())
    }

    private suspend fun isForecastCached(): Boolean {
        return getForecastFromDbUseCase.invoke() != null
    }
}