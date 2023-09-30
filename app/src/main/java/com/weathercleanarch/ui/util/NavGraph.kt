package com.weathercleanarch.ui.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.weathercleanarch.ui.screen.forecast.HomeScreen
import com.weathercleanarch.ui.screen.search_city.SearchCityScreen

@Composable
fun NavGraph(
    startDestination: String = Screen.HomeScreen.route,
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Screen.HomeScreen.route) {
                HomeScreen(onNavigateToSearchCityScreen = { navController.navigate(Screen.SearchScreen.route) })
            }
            composable(Screen.SearchScreen.route) {
                SearchCityScreen {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.HomeScreen.route)
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object HomeScreen : Screen("Home Screen")
    object SearchScreen : Screen("Search City Screen")
}
