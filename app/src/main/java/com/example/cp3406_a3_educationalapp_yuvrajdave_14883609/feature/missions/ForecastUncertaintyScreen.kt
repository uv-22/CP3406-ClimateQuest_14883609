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

private data class UncertaintyOption(
    val id: String,
    val text: String,
    val isCorrect: Boolean
)

@Composable
fun ForecastUncertaintyScreen(
    onNavigateBack: () -> Unit,
    onAttemptRecorded: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = listOf(
        UncertaintyOption(
            id = "use_backup",
            text = "Rain is possible enough to prepare a backup plan, but it is not guaranteed.",
            isCorrect = true
        ),
        UncertaintyOption(
            id = "rain_guaranteed",
            text = "Rain will definitely happen for exactly 70% of the afternoon.",
            isCorrect = false
        ),
        UncertaintyOption(
            id = "ignore_forecast",
            text = "The forecast gives no useful information, so it should be ignored.",
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
            text = "MISSION 2",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Forecasts are not promises",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "Forecasts describe the chance of future conditions. They help you plan, but they cannot guarantee exactly what will happen.",
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
                    text = "Chance of rain on Saturday afternoon: 70%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Text(
            text = "What is the best way to use this forecast?",
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
                            "You used uncertainty well"
                        } else {
                            "Forecasts are useful evidence"
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
                            "Correct. A 70% chance makes rain important to plan for, but it does not promise that rain will happen."
                        } else {
                            "A percentage chance is not a promise or a reason to ignore the forecast. It helps you make a flexible, evidence-based plan."
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
private fun ForecastUncertaintyScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        ForecastUncertaintyScreen(
            onNavigateBack = {},
            onAttemptRecorded = {}
        )
    }
}