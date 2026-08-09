package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.missions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.weather.WeatherUiState
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme
import kotlin.math.roundToInt

private data class PlanOption(
    val id: String,
    val text: String,
    val isCorrect: Boolean
)

@Composable
fun ForecastDetectiveScreen(
    weatherUiState: WeatherUiState,
    onRefreshWeather: () -> Unit,
    onNavigateBack: () -> Unit,
    onAttemptRecorded: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf(
        PlanOption(
            id = "ignore_forecast",
            text = "Plan a picnic and ignore the rain chance because forecasts are often wrong.",
            isCorrect = false
        ),
        PlanOption(
            id = "backup_plan",
            text = "Plan the picnic, pack a rain jacket, and choose an indoor backup activity.",
            isCorrect = true
        ),
        PlanOption(
            id = "cancel_everything",
            text = "Cancel every outdoor activity because rain is guaranteed.",
            isCorrect = false
        )
    )

    var selectedOptionId by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedOption = options.find { it.id == selectedOptionId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextButton(onClick = onNavigateBack) {
            Text("Back to missions")
        }

        Text(
            text = "MISSION 1",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Forecast detective",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "Use the mission forecast as evidence, then compare it with live conditions for the city you chose manually.",
            style = MaterialTheme.typography.bodyLarge
        )

        LiveWeatherCard(
            weatherUiState = weatherUiState,
            onRefreshWeather = onRefreshWeather
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Mission forecast evidence",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "Temperature: 27°C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "Chance of rain: 60%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "Wind: 30 km/h",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Text(
            text = "Which plan uses the mission forecast evidence best?",
            style = MaterialTheme.typography.titleLarge
        )

        options.forEach { option ->
            OutlinedButton(
                onClick = {
                    selectedOptionId = option.id
                    onAttemptRecorded(option.isCorrect)
                },
                enabled = selectedOption == null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(option.text)
            }
        }

        if (selectedOption != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedOption.isCorrect) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.errorContainer
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (selectedOption.isCorrect) {
                            "Good evidence-based thinking"
                        } else {
                            "Think about uncertainty"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = if (selectedOption.isCorrect) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    )

                    Text(
                        text = if (selectedOption.isCorrect) {
                            "A 60% rain chance does not guarantee rain, but it is a strong reason to prepare a backup plan."
                        } else {
                            "A forecast is evidence, not a guarantee. A 60% rain chance makes a backup plan sensible, without meaning rain is certain."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedOption.isCorrect) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    )

                    Text(
                        text = "Your response is saved only on this device.",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selectedOption.isCorrect) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer
                        }
                    )

                    if (!selectedOption.isCorrect) {
                        Button(
                            onClick = {
                                selectedOptionId = null
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Try again")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LiveWeatherCard(
    weatherUiState: WeatherUiState,
    onRefreshWeather: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Live city conditions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            when (weatherUiState) {
                WeatherUiState.NoCityChosen -> {
                    Text(
                        text = "Choose a city in Settings to view live conditions. ClimateQuest does not use GPS.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                is WeatherUiState.Loading -> {
                    Text(
                        text = "Loading current conditions for ${weatherUiState.city}...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                is WeatherUiState.Success -> {
                    val snapshot = weatherUiState.weatherSnapshot

                    Text(
                        text = "Current conditions for ${snapshot.city}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        text = "Temperature: ${snapshot.temperatureCelsius.roundToInt()}°C",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        text = "Wind: ${snapshot.windSpeedKilometresPerHour.roundToInt()} km/h",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    snapshot.precipitationProbability?.let { probability ->
                        Text(
                            text = "Rain chance in the forecast: $probability%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    Text(
                        text = "Source: Open-Meteo forecast model data. Conditions can change.",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    TextButton(onClick = onRefreshWeather) {
                        Text("Refresh conditions")
                    }
                }

                is WeatherUiState.Error -> {
                    Text(
                        text = "Live conditions for ${weatherUiState.city} could not load. Check your connection and try again.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    TextButton(onClick = onRefreshWeather) {
                        Text("Try again")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1100)
@Composable
private fun ForecastDetectiveScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        ForecastDetectiveScreen(
            weatherUiState = WeatherUiState.NoCityChosen,
            onRefreshWeather = {},
            onNavigateBack = {},
            onAttemptRecorded = {}
        )
    }
}