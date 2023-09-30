package com.weathercleanarch.ui.util.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.weathercleanarch.R

object ForecastIconHelper {
    @Composable
    fun setWeatherType(mainDescription: String): Painter {
        val description = mainDescription.lowercase()
        return when {
            description.contains("thunderstorm") -> painterResource(id = R.drawable.thunderstorm)
            description.contains("cloud") -> painterResource(id = R.drawable.sunny_clouds)
            description.contains("rain") -> painterResource(id = R.drawable.rainy)
            else -> painterResource(id = R.drawable.sunny)
        }
    }
}