package com.weathercleanarch.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.weathercleanarch.ui.util.NavGraph
import com.weathercleanarch.ui.util.ui.theme.WeatherCleanArchAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AskForLocationPermission()
        }
    }

    @Composable
    private fun Content() {
        WeatherCleanArchAppTheme {

            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(color = Color.Transparent)
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
            ) {
                NavGraph()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun AskForLocationPermission() {
        val state = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
        Scaffold {
            when {
                state.status.isGranted -> Content()
                else -> {
                    LaunchedEffect(Unit) {
                        state.launchPermissionRequest()
                    }
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                    ) {
                        Column(Modifier.padding(vertical = 120.dp, horizontal = 16.dp)) {

                            Spacer(Modifier.height(8.dp))
                            //Text("Location permission required", style = MaterialTheme.typography.h6)
                            Spacer(Modifier.height(4.dp))
                        }
                        val context = LocalContext.current
                        Button(
                            modifier = Modifier

                                .fillMaxWidth()
                                .padding(16.dp),
                            onClick = {

                            }) {
                            Text("Go to settings")
                        }
                    }
                }
            }
        }
    }
}
