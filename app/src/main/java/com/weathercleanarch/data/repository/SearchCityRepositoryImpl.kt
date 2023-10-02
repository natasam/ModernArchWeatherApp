package com.weathercleanarch.data.repository

import com.weathercleanarch.data.datasource.local.database.LocationLocalDataSource
import com.weathercleanarch.data.mapper.SearchedCityEntityMapper
import com.weathercleanarch.data.mapper.SearchedCityEntityMapper.mapFromDbLocationList
import com.weathercleanarch.domain.entity.SelectedCity
import com.weathercleanarch.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchCityRepositoryImpl @Inject constructor(
    private val locationLocalDataSource: LocationLocalDataSource
) : CityRepository {

    override suspend fun addCity(selectedCity: SelectedCity) = withContext(Dispatchers.Default) {
        locationLocalDataSource.addMyCity(
            SearchedCityEntityMapper.entityFromModel(
                selectedCity
            )
        )
    }

    override suspend fun savedCityList(): List<SelectedCity> {
        return locationLocalDataSource.getMyCity().mapFromDbLocationList()
    }

    override suspend fun deleteCity(cityName: String) {

        return withContext(Dispatchers.Default) {
            locationLocalDataSource.deleteMyCity(cityName)
        }
    }

    override suspend fun updateCity(selectedCity: SelectedCity) {
        return withContext(Dispatchers.Default) {
            locationLocalDataSource.updateMyCity(
                temp = selectedCity.temp,
                latitude = selectedCity.latitude,
                longitude = selectedCity.longitude,
                cityName = selectedCity.cityName,
                description = selectedCity.description,
                weatherImage = selectedCity.weatherImageDesc
            )
        }
    }

    override suspend fun getSpecificCity(cityName: String): Boolean {
        return withContext(Dispatchers.Default) {

            locationLocalDataSource.getSpecificCity(cityName) != null
        }
    }
}