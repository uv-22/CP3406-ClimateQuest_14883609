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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.mission.Mission
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme

@Composable
fun MissionsScreen(
    missions: List<Mission>,
    onMissionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "MISSIONS",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Build your weather-thinking skills.",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "Each short mission helps you use evidence, explain uncertainty, and tell weather from climate.",
            style = MaterialTheme.typography.bodyLarge
        )

        if (missions.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Loading your mission library...",
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        } else {
            missions.forEachIndexed { index, mission ->
                MissionCard(
                    mission = mission,
                    position = index + 1,
                    onMissionSelected = onMissionSelected
                )
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
                    text = "Learn with evidence",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(
                    text = "ClimateQuest helps you make thoughtful claims from information, rather than treating a forecast as a guarantee.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun MissionCard(
    mission: Mission,
    position: Int,
    onMissionSelected: (String) -> Unit
) {
    val isAvailable = mission.id == "forecast_detective"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("mission_${mission.id}")
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "MISSION $position | ${mission.estimatedMinutes} MINUTES",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = mission.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = mission.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Focus: ${mission.skill}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            if (isAvailable) {
                Button(
                    onClick = {
                        onMissionSelected(mission.id)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start mission")
                }
            } else {
                Text(
                    text = "Available soon",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun MissionsScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        MissionsScreen(
            missions = listOf(
                Mission(
                    id = "forecast_detective",
                    title = "Forecast detective",
                    description = "Use temperature, rain chance, and wind to plan a weekend activity with evidence.",
                    skill = "Reading weather evidence",
                    estimatedMinutes = 6
                ),
                Mission(
                    id = "forecast_uncertainty",
                    title = "Forecasts are not promises",
                    description = "Explore why a forecast can be useful even when the weather turns out differently.",
                    skill = "Explaining uncertainty",
                    estimatedMinutes = 5
                )
            ),
            onMissionSelected = {}
        )
    }
}