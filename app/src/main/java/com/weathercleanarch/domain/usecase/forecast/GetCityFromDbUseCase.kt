package com.weathercleanarch.domain.usecase.forecast

import com.weathercleanarch.data.repository.ForecastRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCityFromDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend operator fun invoke() = withContext(
        Dispatchers.Default
    ) { forecastRepositoryImpl.getCity() }
}