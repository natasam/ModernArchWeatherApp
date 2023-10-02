package com.weathercleanarch._di

import com.weathercleanarch.data.repository.ForecastRepositoryImpl
import com.weathercleanarch.data.repository.GeolocationRepositoryImpl
import com.weathercleanarch.domain.repository.ForecastRepository
import com.weathercleanarch.domain.repository.GeolocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindForecastRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(geolocationRepositoryImpl: GeolocationRepositoryImpl): GeolocationRepository


}