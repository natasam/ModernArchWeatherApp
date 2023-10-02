package com.weathercleanarch.ui.screen.search_city

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weathercleanarch.domain.entity.Result
import com.weathercleanarch.domain.entity.SelectedCity
import com.weathercleanarch.domain.usecase.forecast.GetForecastWithCityNameUseCase
import com.weathercleanarch.domain.usecase.saved_cities.AddSavedCityUseCase
import com.weathercleanarch.domain.usecase.saved_cities.DeleteSavedCityUseCase
import com.weathercleanarch.domain.usecase.saved_cities.GetSavedCitiesUseCase
import com.weathercleanarch.domain.usecase.saved_cities.GetSpecificCityUseCase
import com.weathercleanarch.domain.usecase.saved_cities.UpdateSavedCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val getForecastWithCityName: GetForecastWithCityNameUseCase,
    private val getSavedCitiesUseCase: GetSavedCitiesUseCase,
    private val addSavedCityUseCase: AddSavedCityUseCase,
    private val deleteSavedCityUseCase: DeleteSavedCityUseCase,
    private val updateSavedCityUseCase: UpdateSavedCityUseCase,
    private val getSpecificCityUseCase: GetSpecificCityUseCase
) : ViewModel() {

    private val _searchCityState = MutableStateFlow<SearchCityUiState>(SearchCityUiState.Loading)
    val searchCityState = _searchCityState.asStateFlow()

    private val _savedCitiesState = MutableStateFlow<SavedCitiesUiState>(SavedCitiesUiState.Loading)
    val myCitiesState = _savedCitiesState.asStateFlow()

    var searchFieldValue by mutableStateOf("")
        private set

    var isCitySearched by mutableStateOf(false)
        private set

    init {
        loadMyCities()
    }

    fun errorOnClick() {
        _searchCityState.value = SearchCityUiState.Success(null)
    }

    fun searchCityClick() {
        isCitySearched = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (checkSearchFieldValue()) {
                    fetchForecastWithCityName(searchFieldValue)
                } else {
                    _searchCityState.value = SearchCityUiState.Error("Fill in the field")
                }
            } catch (e: Exception) {
                _searchCityState.value = SearchCityUiState.Error(e.message)
            }
        }
    }

    private fun loadMyCities() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isMyCitiesExist()) {
                    updateMyCity()
                } else {
                    _savedCitiesState.value = SavedCitiesUiState.Success(emptyList())
                }
            } catch (e: Exception) {
                _savedCitiesState.value = SavedCitiesUiState.Error(e.message)
            }
        }
    }

    private suspend fun fetchForecastWithCityName(cityName: String) {
        when (val result = getForecastWithCityName.invoke(cityName)) {
            is Result.Success -> {
                _searchCityState.value = SearchCityUiState.Success(result.data)
            }

            is Result.Error -> {
                _searchCityState.value = SearchCityUiState.Error(result.message)
            }

            else -> {}
        }
    }

    fun addMyCity(selectedCity: SelectedCity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!getSpecificCityUseCase.invoke(selectedCity.cityName)) {
                    addSavedCityUseCase.invoke(selectedCity)
                    loadMyCities()
                } else {
                    Log.e("add city", "you have already added this city")
                }
            } catch (e: Exception) {
            }
        }
    }

    fun removeMyCity(cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteSavedCityUseCase.invoke(cityName)
                loadMyCities()
            } catch (e: Exception) {
                Log.e("e", e.message.toString())
            }
        }
    }

    // no internet connection -> load cities from database
    // internet connection -> update our cities
    private fun updateMyCity() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSavedCitiesUseCase.invoke().forEach { myCity ->
                    when (val result = getForecastWithCityName.invoke(myCity.cityName)) {
                        is Result.Success -> {
                            if (result.data != null) {
                                updateSavedCityUseCase.invoke(
                                    SelectedCity(
                                        temp = result.data.weatherList[0].weatherData.temp,
                                        latitude = result.data.city.coordinate.latitude,
                                        longitude = result.data.city.coordinate.longitude,
                                        cityName = result.data.city.cityName,
                                        country = result.data.city.country,
                                        description = result.data.weatherList[0].weatherStatus[0].description,
                                        weatherImageDesc = result.data.weatherList[0].weatherStatus[0].mainDescription,

                                        )
                                )
                                _savedCitiesState.value =
                                    SavedCitiesUiState.Success(getSavedCitiesUseCase.invoke())
                            }
                        }

                        is Result.Error -> {
                            if (result.message?.contains("Unable to resolve host.") == true) {
                                _savedCitiesState.value =
                                    SavedCitiesUiState.Success(getSavedCitiesUseCase.invoke())
                            } else {
                                _savedCitiesState.value = SavedCitiesUiState.Error(result.message)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("exception", e.message.toString())
            }
        }
    }

    private fun checkSearchFieldValue(): Boolean {
        return searchFieldValue.isNotEmpty()
    }

    fun updateSearchField(input: String) {
        searchFieldValue = input
    }

    private suspend fun isMyCitiesExist(): Boolean {
        return getSavedCitiesUseCase.invoke().isNotEmpty()
    }
}