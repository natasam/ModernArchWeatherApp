package com.weathercleanarch.ui.screen.forecast

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.weathercleanarch.R
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.ui.screen.forecast.view.CurrentWeatherDetailRow
import com.weathercleanarch.ui.screen.forecast.view.ForecastLazyRow
import com.weathercleanarch.ui.screen.forecast.view.ForecastTitle
import com.weathercleanarch.ui.util.ui.CircularProgressBar
import com.weathercleanarch.ui.util.ui.ErrorMessageView
import com.weathercleanarch.ui.util.ui.NeuBrutalismHelper.applyNeuBrutalism

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onNavigateToSearchCityScreen: () -> Unit
) {
    val homeScreenState by viewModel.homeForecastState.collectAsState()
    val activity = (LocalContext.current as? Activity)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color("#00C2FF".toColorInt())
    ) {
        WeatherSection(homeScreenState) { activity?.finish() }
        MenuIcon { onNavigateToSearchCityScreen() }
    }
}

@Composable
private fun WeatherSection(
    currentWeatherState: ForecastUiState,
    errorCardOnClick: () -> Unit
) {
    when (currentWeatherState) {
        is ForecastUiState.Loading -> LoadingState()
        is ForecastUiState.Success -> SuccessState(currentWeatherState)
        is ForecastUiState.Error -> ErrorState(currentWeatherState, errorCardOnClick)
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 5))
    }
}

@Composable
private fun SuccessState(currentWeatherState: ForecastUiState.Success) {
    currentWeatherState.forecast?.run {
        ScreenDetailsSection(this)
    }
}

@Composable
private fun ErrorState(
    currentWeatherState: ForecastUiState.Error,
    errorCardOnClick: () -> Unit
) {
    currentWeatherState.exception?.let {
        ErrorMessageView(
            modifier = Modifier.fillMaxSize(),
            exception = it,
            errorCardOnClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScreenDetailsSection(forecast: Forecast) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        stickyHeader { CurrentWeatherSection(forecast, listState) }
        item { WeatherDetailSection(forecast) }
        item { ForecastSection(forecast) }

    }
}

@Composable
private fun CurrentWeatherSection(todayWeather: Forecast, lazyListState: LazyListState) {
    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 60.dp),
        ) {
            Text(
                text = todayWeather.city.cityName, style = MaterialTheme.typography.h3
            )
            Text(
                modifier = Modifier.offset(x = 2.dp, y = 2.dp),
                text = "${todayWeather.weatherList[0].weatherData.temp.toInt()}°C",
                style = MaterialTheme.typography.h1.copy(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(12f, 20f),
                    )
                )
            )
            Column(modifier = Modifier.alpha(if (!lazyListState.canScrollForward) 0f else 1f)) {
                DetailTexts(todayWeather)
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun DetailTexts(todayWeather: Forecast) {
    val description = todayWeather.weatherList[0].weatherStatus[0].description
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = description,
            style = MaterialTheme.typography.h3,
            color = Color.Yellow
        )
        Text(
            text = stringResource(
                R.string.feels_like_,
                todayWeather.weatherList[0].weatherData.feelsLike
            ),
            style = MaterialTheme.typography.h3
        )
    }
}

@Composable
private fun ForecastSection(forecastData: Forecast) {
    ForecastTitle(text = stringResource(R.string.hourly_forecast))
    ForecastLazyRow(forecasts = forecastData.weatherList.take(8))
    ForecastTitle(text = stringResource(R.string.daily_forecast))
    ForecastLazyRow(forecasts = forecastData.weatherList.subList(9, forecastData.weatherList.size))
    Spacer(Modifier.height(28.dp))
}

@Composable
private fun WeatherDetailSection(currentWeather: Forecast) {
    val forecast = currentWeather.weatherList[0]

    CurrentWeatherDetailRow(
        title1 = stringResource(R.string.clouds),
        value1 = "${forecast.cloudiness.cloudiness}%",
        title2 = stringResource(R.string.humidity),
        value2 = "${forecast.weatherData.humidity}%"
    )
    Spacer(modifier = Modifier.size(4.dp))
    CurrentWeatherDetailRow(
        title1 = stringResource(R.string.wind),
        value1 = stringResource(R.string.km, forecast.wind.speed),
        title2 = stringResource(R.string.pressure),
        value2 = "${forecast.weatherData.pressure}"
    )
}

@Composable
private fun MenuIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 24.dp, end = 24.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .applyNeuBrutalism(
                    backgroundColor = Color.White,
                    borderWidth = 3.dp,
                ),
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}