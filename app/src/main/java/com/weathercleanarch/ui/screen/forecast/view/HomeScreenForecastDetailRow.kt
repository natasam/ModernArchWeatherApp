package com.weathercleanarch.ui.screen.forecast.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weathercleanarch.ui.util.ui.NeuBrutalismHelper.applyNeuBrutalism

@Composable
fun CurrentWeatherDetailRow(title1: String, value1: String, title2: String, value2: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CurrentWeatherDetailCard(title = title1, value = value1)
        Spacer(modifier = Modifier.size(4.dp))
        CurrentWeatherDetailCard(title = title2, value = value2)
    }
}

@Composable
private fun CurrentWeatherDetailCard(title: String, value: String) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .height(80.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .applyNeuBrutalism(
                    backgroundColor = Color.Yellow,
                    borderWidth = 3.dp
                ), Alignment.TopStart
        ) {
            Text(
                text = title, modifier =
                Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                color = Color.Black,
                style = MaterialTheme.typography.h3.copy(fontSize = 15.sp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    BorderStroke(2.dp, SolidColor(Color.Black))
                )
                .applyNeuBrutalism(
                    backgroundColor = Color.Yellow,
                    borderWidth = 2.dp
                ), Alignment.Center
        ) {
            Text(
                text = value, color = Color.Black,
                style = MaterialTheme.typography.h2.copy(fontSize = 26.sp)
            )
        }
    }
}