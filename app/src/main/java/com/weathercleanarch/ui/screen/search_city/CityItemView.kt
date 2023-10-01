package com.weathercleanarch.ui.screen.search_city

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weathercleanarch.R
import com.weathercleanarch.ui.util.ui.ForecastIconHelper
import com.weathercleanarch.ui.util.ui.NeuBrutalismHelper.applyNeuBrutalism

data class WeatherInfo(
    val degree: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val country: String,
    val description: String
)

@Composable
fun CityWeatherCard(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo,
    onClick: () -> Unit = {},
    isStoredInDb: Boolean = false
) {
    Box(modifier = modifier) {
        WeatherInfoView(weatherInfo, onClick, isStoredInDb)
        WeatherImage(weatherInfo.description)
    }
}

@Composable
private fun WeatherImage(weatherImageDesc: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp, end = 42.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = Modifier.size(90.dp),
            painter = ForecastIconHelper.setWeatherType(weatherImageDesc),
            contentDescription = "Weather Icon"
        )
    }
}

@Composable
private fun WeatherInfoView(
    weatherInfo: WeatherInfo,
    onClick: () -> Unit,
    isStoredInDb: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .applyNeuBrutalism(
                backgroundColor = Color.White,
                borderWidth = 3.dp,
                cornersRadius = 18.dp
            )
    ) {
        ForecastView(weatherInfo.degree, isStoredInDb, onClick)
        LocationInfoView(weatherInfo)
    }
}

@Composable
private fun ForecastView(degree: String, isStoredInDb: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = degree, fontSize = 36.sp)
        IconButtonSection(isStoredInDb, onClick)
    }
}

@Composable
private fun LocationInfoView(weatherInfo: WeatherInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = "${weatherInfo.city}, ${weatherInfo.country}",
                style = MaterialTheme.typography.h3
            )
            Text(
                text = "${weatherInfo.latitude}, ${weatherInfo.longitude}",
                style = MaterialTheme.typography.body2
            )
        }
        Text(modifier = Modifier.padding(end = 26.dp), text = weatherInfo.description)
    }
}

@Composable
private fun IconButtonSection(isStoredInDb: Boolean, onClick: () -> Unit) {
    val size = if (isStoredInDb) 25.dp else 32.dp
    val tint = if (isStoredInDb) Color.White else Color.Yellow
    val painterId = if (isStoredInDb) Icons.Default.Clear else Icons.Default.Add
    val description =
        if (isStoredInDb) stringResource(R.string.close) else stringResource(R.string.add)
    IconButton(onClick = onClick) {
        Icon(
            modifier = Modifier
                .size(size)
                .applyNeuBrutalism(
                    backgroundColor = tint,
                    borderWidth = 2.dp,

                    ),
            imageVector = painterId,
            contentDescription = description
        )
    }
}
