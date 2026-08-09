package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.progress

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptEntity
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme

@Composable
fun ProgressScreen(
    progressUiState: ProgressUiState,
    onStartMission: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accuracy = if (progressUiState.totalAttempts == 0) {
        0
    } else {
        (progressUiState.correctAttempts * 100) / progressUiState.totalAttempts
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "PROGRESS",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Your learning trail",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "Your mission responses are saved only on this device. You can use them to notice how your weather-thinking skills are growing.",
            style = MaterialTheme.typography.bodyLarge
        )

        if (progressUiState.totalAttempts == 0) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Ready for your first mission?",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = "Complete Forecast detective to begin your personal learning trail.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Button(
                        onClick = onStartMission,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Explore missions")
                    }
                }
            }
        } else {
            ProgressSummaryCard(
                title = "Responses saved",
                value = progressUiState.totalAttempts.toString(),
                description = "Each answer helps you practise reasoning from evidence."
            )

            ProgressSummaryCard(
                title = "Evidence-based answers",
                value = "${progressUiState.correctAttempts} of ${progressUiState.totalAttempts}",
                description = "$accuracy% of your saved responses used the strongest available evidence."
            )

            ProgressSummaryCard(
                title = "Missions completed",
                value = progressUiState.completedMissionIds.size.toString(),
                description = "A mission counts as completed when you choose its evidence-based answer."
            )

            Text(
                text = "Recent activity",
                style = MaterialTheme.typography.titleLarge
            )

            progressUiState.recentAttempts.forEach { attempt ->
                RecentAttemptCard(attempt = attempt)
            }
        }

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
                    text = "Your data, your control",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(
                    text = "ClimateQuest stores learning progress locally. It does not use accounts, ads, analytics, or background tracking.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun ProgressSummaryCard(
    title: String,
    value: String,
    description: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = value,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun RecentAttemptCard(
    attempt: MissionAttemptEntity
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = missionTitle(attempt.missionId),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = if (attempt.wasCorrect) {
                    "Evidence-based answer saved"
                } else {
                    "Practice response saved"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (attempt.wasCorrect) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                }
            )
        }
    }
}

private fun missionTitle(missionId: String): String {
    return when (missionId) {
        "forecast_detective" -> "Forecast detective"
        else -> "ClimateQuest mission"
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun ProgressScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        ProgressScreen(
            progressUiState = ProgressUiState(
                totalAttempts = 3,
                correctAttempts = 2,
                completedMissionIds = setOf("forecast_detective"),
                recentAttempts = listOf(
                    MissionAttemptEntity(
                        id = 1,
                        missionId = "forecast_detective",
                        wasCorrect = true,
                        completedAtEpochMillis = 0
                    ),
                    MissionAttemptEntity(
                        id = 2,
                        missionId = "forecast_detective",
                        wasCorrect = false,
                        completedAtEpochMillis = 0
                    )
                )
            ),
            onStartMission = {}
        )
    }
}