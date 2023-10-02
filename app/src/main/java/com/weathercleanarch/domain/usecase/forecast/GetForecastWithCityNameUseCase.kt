package com.weathercleanarch.domain.usecase.forecast

import com.weathercleanarch.data.repository.ForecastRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetForecastWithCityNameUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend operator fun invoke(cityName: String) = withContext(Dispatchers.Default) {
        forecastRepositoryImpl.getForecastDataWithCityName(cityName)
    }
}