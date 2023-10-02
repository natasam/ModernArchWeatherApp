package com.weathercleanarch.data.mapper


import com.weathercleanarch.data.datasource.local.database.model.CityDbDto
import com.weathercleanarch.data.datasource.local.database.model.ForecastDbDto
import com.weathercleanarch.data.datasource.remote.response.ForecastResponse
import com.weathercleanarch.domain.entity.City
import com.weathercleanarch.domain.entity.Cloudiness
import com.weathercleanarch.domain.entity.Coord
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.ForecastWeather
import com.weathercleanarch.domain.entity.Main
import com.weathercleanarch.domain.entity.Weather
import com.weathercleanarch.domain.entity.Wind

fun ForecastResponse.toForecastDto(): Forecast {
    val forecastWeather: List<ForecastWeather> = weatherList.map {
        ForecastWeather(
            weatherData = Main(
                it.mainWeatherInfo.temp,
                it.mainWeatherInfo.feelsLike,
                it.mainWeatherInfo.pressure,
                it.mainWeatherInfo.humidity
            ),
            weatherStatus = listOf(
                Weather(it.weatherStatus[0].mainDescription, it.weatherStatus[0].description)
            ),
            wind = Wind(it.wind.speed),
            date = it.date,
            cloudiness = Cloudiness(it.cloudinessDto.cloudiness)
        )
    }

    return Forecast(
        weatherList = forecastWeather,
        city = City(
            city.country,
            city.timezone,
            city.sunrise,
            city.sunset,
            city.cityName,
            Coord(
                city.coordinate.latitude, city.coordinate.longitude
            )
        )
    )
}

fun Forecast.mapToDbEntity(): ForecastDbDto {
    val forecastWeather = weatherList[0]
    return ForecastDbDto(
        id = forecastWeather.id,
        temp = forecastWeather.weatherData.temp,
        feels_like = forecastWeather.weatherData.feelsLike,
        pressure = forecastWeather.weatherData.pressure,
        humidity = forecastWeather.weatherData.humidity,
        wind_speed = forecastWeather.wind.speed,
        description = forecastWeather.weatherStatus[0].description,
        mainDescription = forecastWeather.weatherStatus[0].mainDescription,
        date = forecastWeather.date,
        cloudiness = forecastWeather.cloudiness.cloudiness
    )

}

fun mapFromDbDtoList(forecastDbList: List<ForecastDbDto>, entityCity: CityDbDto): Forecast {
    return Forecast(
        forecastDbList.map {
            ForecastWeather(
                it.id,
                Main(
                    it.temp,
                    it.feels_like,
                    it.pressure,
                    it.humidity
                ),
                listOf(
                    Weather(it.mainDescription, it.description)
                ),
                Wind(it.wind_speed),
                it.date,
                Cloudiness(it.cloudiness)
            )
        },
        City(
            entityCity.country,
            entityCity.timezone,
            entityCity.sunrise,
            entityCity.sunset,
            entityCity.cityName,
            Coord(
                entityCity.longitude,
                entityCity.latitude
            )
        )
    )
}

