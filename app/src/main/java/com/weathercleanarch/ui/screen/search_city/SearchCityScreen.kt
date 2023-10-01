@file:OptIn(ExperimentalComposeUiApi::class)

package com.weathercleanarch.ui.screen.search_city

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.weathercleanarch.R
import com.weathercleanarch.domain.entity.Forecast
import com.weathercleanarch.domain.entity.SelectedCity
import com.weathercleanarch.domain.entity.WeatherException
import com.weathercleanarch.ui.util.ui.CircularProgressBar
import com.weathercleanarch.ui.util.ui.ErrorMessageView
import com.weathercleanarch.ui.util.ui.NeuBrutalismHelper.applyNeuBrutalism

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchCityScreen(viewModel: SearchCityViewModel = hiltViewModel(), onNavigateToHomeScreen: () -> Unit) {
    val searchCityState by viewModel.searchCityState.collectAsState()
    val savedCitiesState by viewModel.myCitiesState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor =
        Color("#00C2FF".toColorInt()),
        topBar = { TopBarSection(onNavigateToHomeScreen) },
        ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            SearchCityScreenContent(
                viewModel = viewModel,
                searchCityState = searchCityState,
                savedCitiesState = savedCitiesState,
                keyboardController
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchCityScreenContent(
    viewModel: SearchCityViewModel,
    searchCityState: SearchCityUiState,
    savedCitiesState: SavedCitiesUiState,
    keyboardController: SoftwareKeyboardController?
) {
    SearchField(viewModel, keyboardController)

    if (viewModel.isCitySearched) {
        when (searchCityState) {
            is SearchCityUiState.Loading -> LoadingComponent()
            is SearchCityUiState.Success -> searchCityState.forecast?.let {
                SearchCityResultSection(
                    it,
                    viewModel
                )
            }

            is SearchCityUiState.Error -> SearchResultErrorMessage(viewModel)
        }
    }
    SavedCities(savedCitiesState, viewModel)
}

@Composable
private fun LoadingComponent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressBar(
            modifier = Modifier
                .size(LocalConfiguration.current.screenWidthDp.dp / 5)
                .padding(top = 16.dp)
        )
    }
}

@Composable
private fun TopBarSection(onBackClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .statusBarsPadding()
            .padding(start = 16.dp),
        title = {
            Text(
                text = stringResource(R.string.weather),
                style = MaterialTheme.typography.h2
            )
        },
        navigationIcon = {
            BackButton(onBackClick)
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
private fun BackButton(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .applyNeuBrutalism(
                    backgroundColor = Color.White,
                    borderWidth = 3.dp
                )
                .padding(4.dp),
            imageVector =  Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchField(
    viewModel: SearchCityViewModel,
    keyboardController: SoftwareKeyboardController?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .applyNeuBrutalism(
                backgroundColor = Color.Yellow,
                borderWidth = 2.dp
            ), contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.searchFieldValue,
            onValueChange = { viewModel.updateSearchField(it) },
            label = {
                Text(text = stringResource(R.string.search_for_a_city))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.searchCityClick()
                    keyboardController?.hide()
                }) {
                    Icon(
                        tint = Color.Black,
                        imageVector =  Icons.Default.Search,
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Composable
private fun SearchCityResultSection(forecast: Forecast, viewModel: SearchCityViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(text = stringResource(R.string.saved_cities), style = MaterialTheme.typography.h2)
        val weatherInfo = WeatherInfo(
            degree = "${forecast.weatherList[0].weatherData.temp.toInt()}°C",
            latitude = forecast.city.coordinate.latitude,
            longitude = forecast.city.coordinate.longitude,
            city = forecast.city.cityName,
            country = forecast.city.country,
            description = forecast.weatherList[0].weatherStatus[0].description,
        )
        CityWeatherCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            weatherInfo = weatherInfo,
            isStoredInDb = false,
            onClick = {
                viewModel.addMyCity(
                    SelectedCity(
                        temp = forecast.weatherList[0].weatherData.temp,
                        latitude = forecast.city.coordinate.latitude,
                        longitude = forecast.city.coordinate.longitude,
                        cityName = forecast.city.cityName,
                        country = forecast.city.country,
                        description = forecast.weatherList[0].weatherStatus[0].description,
                        weatherImageDesc =
                        forecast.weatherList[0].weatherStatus[0].mainDescription
                    )
                )
            }
        )

    }


}

@Composable
private fun SavedCities(savedCitiesState: SavedCitiesUiState, viewModel: SearchCityViewModel) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        when (savedCitiesState) {
            is SavedCitiesUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressBar(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3))
                }
            }

            is SavedCitiesUiState.Success -> {

                if (savedCitiesState.forecast.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        EmptyCityListMessage()
                    }
                } else {
                    SavedListSection(savedCitiesState.forecast, viewModel)
                }
            }

            is SavedCitiesUiState.Error -> {
                CityListErrorMessage(savedCitiesState.errorMessage)
            }
        }
    }
}

@Composable
private fun SearchResultErrorMessage(viewModel: SearchCityViewModel) {
    ErrorMessageView(
        modifier = Modifier
            .fillMaxWidth().height(260.dp)

            .padding(top = 16.dp),
        exception = WeatherException.UnknownError,
        onClick = { viewModel.errorOnClick() },
    )
}

@Composable
private fun EmptyCityListMessage() {
    Image(
        modifier = Modifier
            .size(128.dp)
            .padding(bottom = 16.dp),
       imageVector =  Icons.Default.Search,
        contentDescription = null
    )
    Text(text = "Your list is empty.")
}

@Composable
private fun SavedListSection(cityList: List<SelectedCity>, viewModel: SearchCityViewModel) {
    Text(
        text = stringResource(R.string.saved_results),
        style = MaterialTheme.typography.h2
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(cityList.reversed()) {
            val weatherInfo = WeatherInfo(
                degree = "${it.temp.toInt()}°C",
                latitude = it.latitude,
                longitude = it.longitude,
                city = it.cityName,
                country = it.country,
                description = it.description
            )
            CityWeatherCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                weatherInfo = weatherInfo,
                isStoredInDb = true,
                onClick = { viewModel.removeMyCity(it.cityName) }
            )
        }
    }
}

@Composable
private fun CityListErrorMessage(errorMessage: String?) {
    ErrorMessageView(
        exception = WeatherException.UnknownErrorWithMsg(msg = errorMessage),
        onClick = {}
    )
}