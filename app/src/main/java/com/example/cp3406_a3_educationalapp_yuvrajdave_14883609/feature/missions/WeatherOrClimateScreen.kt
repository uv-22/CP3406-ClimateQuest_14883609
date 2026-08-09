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
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme

private data class ClimateOption(
    val id: String,
    val text: String,
    val isCorrect: Boolean
)

@Composable
fun WeatherOrClimateScreen(
    onNavigateBack: () -> Unit,
    onAttemptRecorded: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf(
        ClimateOption(
            id = "weather_not_climate",
            text = "It describes today's weather. One rainy day does not show what the climate is like.",
            isCorrect = true
        ),
        ClimateOption(
            id = "permanent_climate",
            text = "It proves Townsville's climate is permanently rainy.",
            isCorrect = false
        ),
        ClimateOption(
            id = "same_thing",
            text = "It proves weather and climate mean exactly the same thing.",
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
            text = "MISSION 3",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Weather or climate?",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "Weather describes conditions over a short time. Climate describes patterns measured over many years.",
            style = MaterialTheme.typography.bodyLarge
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
                    text = "Observation",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "It rained in Townsville this afternoon.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Text(
            text = "What can this observation tell us?",
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
                            "You separated weather from climate"
                        } else {
                            "Check the time scale"
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
                            "Exactly. Rain this afternoon is weather: a short-term condition. Climate needs evidence collected across many years."
                        } else {
                            "One afternoon of rain is weather, not climate. Climate describes longer-term patterns, usually measured across decades."
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

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun WeatherOrClimateScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        WeatherOrClimateScreen(
            onNavigateBack = {},
            onAttemptRecorded = {}
        )
    }
}