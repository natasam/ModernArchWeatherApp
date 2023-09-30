package com.weathercleanarch.data.datasource.local.database.db

import androidx.room.*
import com.weathercleanarch.data.datasource.local.database.model.CityDbDto

@Dao
interface CityDao {
    @Insert
    suspend fun addCity(cityDbDto: CityDbDto)

    @Query("SELECT * FROM city_table")
    fun getCity(): CityDbDto

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCity(cityDbDto: CityDbDto)
}