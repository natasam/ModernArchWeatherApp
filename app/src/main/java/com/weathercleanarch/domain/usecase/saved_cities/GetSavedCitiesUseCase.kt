package com.weathercleanarch.domain.usecase.saved_cities

import com.weathercleanarch.data.repository.SearchCityRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSavedCitiesUseCase @Inject constructor(private val mySearchCityRepositoryImpl: SearchCityRepositoryImpl) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        mySearchCityRepositoryImpl.savedCityList()
    }
}