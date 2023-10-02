package com.weathercleanarch.domain.usecase.forecast

import com.weathercleanarch.data.repository.ForecastRepositoryImpl
import com.weathercleanarch.domain.entity.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddCityToDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend operator fun invoke(city: City) = withContext(Dispatchers.IO) {
        forecastRepositoryImpl.addCity(city)
    }
}