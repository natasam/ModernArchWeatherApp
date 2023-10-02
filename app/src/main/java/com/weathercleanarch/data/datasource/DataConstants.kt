package com.weathercleanarch.data.datasource

object NetworkService {
    const val BASE_URL: String = "https://api.openweathermap.org"
    const val API_KEY: String = "API_KEY"
    const val UNITS: String = "metric"
    const val FORECAST_END_POINT = "/data/2.5/forecast"
}

object Database {
    const val forecast_table = "forecast_table"
    const val database_name = "forecast_data.db"
    const val city_table = "city_table"
    const val location_city_table = "location_city_table"
}




