package com.weathercleanarch.data.datasource.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weathercleanarch.data.datasource.Database

@Entity(tableName = Database.location_city_table)
data class LocationCityDbDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "temp")
    var temp: Double,

    @ColumnInfo(name = "latitude")
    var latitude: Double,

    @ColumnInfo(name = "longitude")
    var longitude: Double,

    @ColumnInfo(name = "city")
    var cityName: String,

    @ColumnInfo(name = "country")
    var country: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "weather_image_desc")
    var weatherImageDesc: String
)
