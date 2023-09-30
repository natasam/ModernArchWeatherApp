package com.weathercleanarch.domain.usecase.saved_cities

import com.weathercleanarch.data.repository.SearchCityRepositoryImpl
import com.weathercleanarch.domain.entity.SelectedCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddSavedCityUseCase @Inject constructor(private val mySearchCityRepositoryImpl: SearchCityRepositoryImpl) {
    suspend operator fun invoke(selectedCity: SelectedCity) = withContext(Dispatchers.IO){
        mySearchCityRepositoryImpl.addCity(selectedCity)
    }
}