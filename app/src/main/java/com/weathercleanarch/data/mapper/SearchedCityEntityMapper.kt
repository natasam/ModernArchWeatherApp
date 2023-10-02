package com.weathercleanarch.data.mapper

import com.weathercleanarch.data.datasource.local.database.model.CityDbDto
import com.weathercleanarch.data.datasource.local.database.model.LocationCityDbDto
import com.weathercleanarch.domain.entity.City
import com.weathercleanarch.domain.entity.Coord
import com.weathercleanarch.domain.entity.SelectedCity

object SearchedCityEntityMapper {
    fun List<LocationCityDbDto>.mapFromDbLocationList(): List<SelectedCity> {
        return this.map {
            SelectedCity(
                it.temp,
                it.latitude,
                it.longitude,
                it.cityName,
                it.country,
                it.description,
                it.weatherImageDesc,
            )
        }
    }

    fun entityFromModel(model: SelectedCity): LocationCityDbDto {
        return LocationCityDbDto(
            temp = model.temp,
            latitude = model.latitude,
            longitude = model.longitude,
            cityName = model.cityName,
            country = model.country,
            description = model.description,
            weatherImageDesc = model.weatherImageDesc
        )
    }

    fun CityDbDto.toCityEntity() = City(
        country,
        timezone,
        sunrise,
        sunset,
        cityName,
        Coord(
            latitude,
            longitude
        )
    )


    fun City.toCityDbDto() = CityDbDto(
        id = 1,
        country = country,
        timezone = timezone,
        sunrise = sunrise,
        sunset = sunset,
        cityName = cityName,
        latitude = coordinate.latitude,
        longitude = coordinate.longitude
    )

}