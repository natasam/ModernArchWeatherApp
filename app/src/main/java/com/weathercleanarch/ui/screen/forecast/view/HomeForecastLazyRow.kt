package com.weathercleanarch.ui.screen.forecast.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weathercleanarch.domain.entity.ForecastWeather
import com.weathercleanarch.ui.util.HourConverter
import com.weathercleanarch.ui.util.ui.ForecastIconHelper.setWeatherType
import com.weathercleanarch.ui.util.ui.NeuBrutalismHelper.applyNeuBrutalism

@Composable
fun ForecastLazyRow(forecasts: List<ForecastWeather>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .applyNeuBrutalism(
                backgroundColor = Color.Yellow,
                borderWidth = 3.dp
            ),

        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) {
            if (forecasts.size == 8) {
                WeatherItem(
                    time = HourConverter.convertHour(it.date.substring(11, 13)),
                    weatherIcon = setWeatherType(
                        it.weatherStatus[0].mainDescription
                    ),
                    degree = "${it.weatherData.temp.toInt()}°"
                )
            } else {
                WeatherItem(
                    date = it.date.substring(5, 10).replace('-', '/'),
                    time = HourConverter.convertHour(it.date.substring(11, 13)),
                    weatherIcon = setWeatherType(
                        it.weatherStatus[0].mainDescription
                    ),
                    degree = "${it.weatherData.temp.toInt()}°"
                )
            }
        }
    }
}

@Composable
private fun WeatherItem(date: String? = null, time: String, weatherIcon: Painter, degree: String) {
    Box {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (date != null) {
                    Text(text = date, style = MaterialTheme.typography.h3.copy(fontSize = 15.sp))
                }
                Text(text = time, style = MaterialTheme.typography.h3.copy(fontSize = 15.sp))
            }
            Image(painter = weatherIcon, contentDescription = null)
            Text(text = degree, style = MaterialTheme.typography.h3.copy(fontSize = 18.sp))
        }
    }
}