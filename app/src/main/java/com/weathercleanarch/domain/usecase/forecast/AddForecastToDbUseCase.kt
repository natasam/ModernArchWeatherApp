package com.weathercleanarch.domain.usecase.forecast

import com.weathercleanarch.data.repository.ForecastRepositoryImpl
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.ForecastWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddForecastToDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend operator fun invoke(forecast: Forecast, forecastSize: Int) =
        withContext(Dispatchers.IO) {
            for (i in 1..forecastSize) {
                forecastRepositoryImpl.addForecastWeather(
                    Forecast(
                        listOf(
                            ForecastWeather(
                                i,
                                forecast.weatherList[i - 1].weatherData,
                                forecast.weatherList[i - 1].weatherStatus,
                                forecast.weatherList[i - 1].wind,
                                forecast.weatherList[i - 1].date,
                                forecast.weatherList[i - 1].cloudiness
                            )
                        ),
                        forecast.city
                    )
                )
            }
        }
}