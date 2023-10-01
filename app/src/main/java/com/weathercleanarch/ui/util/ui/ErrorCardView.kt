package com.weathercleanarch.ui.util.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.weathercleanarch.R
import com.weathercleanarch.domain.entity.WeatherException
import com.weathercleanarch.ui.util.ui.NeuBrutalismHelper.applyNeuBrutalism
import com.weathercleanarch.ui.util.ui.theme.ErrorRed

@Composable
fun ErrorMessageView(
    modifier: Modifier = Modifier,
    exception: WeatherException,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .applyNeuBrutalism(backgroundColor = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = ErrorRed
            )
            Text(
                text = exception.value,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.error
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = setErrorDescription(exception),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(15.dp))
            Button(
                modifier = Modifier.applyNeuBrutalism(
                    backgroundColor = ErrorRed,
                    borderWidth = 2.dp,
                    cornersRadius = 40.dp
                ),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = stringResource(R.string.ok).uppercase(),
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 28.dp)
                )
            }
        }
    }
}

fun setErrorDescription(exception: WeatherException): String {
    return exception.value
}
