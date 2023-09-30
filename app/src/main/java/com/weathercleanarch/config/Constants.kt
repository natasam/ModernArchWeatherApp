package com.weathercleanarch.config

object NetworkService {
    const val BASE_URL: String = "https://api.openweathermap.org"
    const val API_KEY: String = "{YOUR_API_KEY}"
    const val UNITS: String = "metric"
    const val FORECAST_END_POINT = "/data/2.5/forecast"
}

object Database {
    const val forecast_table = "forecast_table"
    const val database_name = "forecast_data.db"
    const val city_table = "city_table"
    const val location_city_table = "location_city_table"
}

object Constants {
    const val UNKNOWN_ERROR = "An unknown error occurred."
    const val FILL_FIELD = "Please fill in the field."
    const val UNKNOWN_HOST = "Unable to resolve host. No address associated with hostname"
}



