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

private data class PlanOption(
    val id: String,
    val text: String,
    val isCorrect: Boolean
)

@Composable
fun ForecastDetectiveScreen(
    onNavigateBack: () -> Unit,
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
            text = "You want to plan a Saturday afternoon at the park. Use the forecast as evidence to choose a sensible plan.",
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
                    text = "Forecast evidence",
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
            text = "Which plan uses the forecast evidence best?",
            style = MaterialTheme.typography.titleLarge
        )

        options.forEach { option ->
            OutlinedButton(
                onClick = {
                    selectedOptionId = option.id
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
private fun ForecastDetectiveScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        ForecastDetectiveScreen(onNavigateBack = {})
    }
}