package com.weathercleanarch.domain.repository

import com.weathercleanarch.domain.entity.SelectedCity

interface CityRepository {
    suspend fun addCity(selectedCity: SelectedCity)
    suspend fun savedCityList(): List<SelectedCity>
    suspend fun deleteCity(cityName: String)
    suspend fun updateCity(selectedCity: SelectedCity)
    suspend fun getSpecificCity(cityName: String): Boolean
}