package com.weathercleanarch.domain.usecase.saved_cities

import com.weathercleanarch.data.repository.SearchCityRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteSavedCityUseCase @Inject constructor(private val mySearchCityRepositoryImpl: SearchCityRepositoryImpl) {
    suspend operator fun invoke(cityName: String) = withContext(Dispatchers.IO) {
        mySearchCityRepositoryImpl.deleteCity(cityName)
    }
}