package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme

@Composable
fun SettingsScreen(
    selectedCity: String?,
    onChooseCity: () -> Unit,
    onClearCity: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showRemoveCityDialog by rememberSaveable { mutableStateOf(false) }
    val cityToRemove = selectedCity

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "SETTINGS",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "You are in control.",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "ClimateQuest keeps your learning choices clear and gives you control over what stays on this device.",
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
                    text = "Privacy at a glance",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "ClimateQuest does not use GPS, accounts, ads, analytics, or background tracking.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "You will choose cities manually, so your location stays yours.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
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
                    text = "City choice",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(
                    text = selectedCity?.let { "Current city: $it" }
                        ?: "No city chosen yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(
                    text = "You choose cities yourself. ClimateQuest does not use GPS.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Button(
                    onClick = onChooseCity,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("choose_city_button")
                ) {
                    Text(
                        text = if (selectedCity == null) {
                            "Choose a city"
                        } else {
                            "Change city"
                        }
                    )
                }

                if (selectedCity != null) {
                    OutlinedButton(
                        onClick = { showRemoveCityDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("remove_city_button")
                    ) {
                        Text("Remove saved city")
                    }
                }
            }
        }

        Text(
            text = "Your learning controls",
            style = MaterialTheme.typography.titleLarge
        )

        LearnerControlCard(
            icon = Icons.Outlined.Tune,
            title = "Learning preferences",
            description = "Choose the help and explanations that make missions easier to understand.",
            status = "Optional learning preferences are coming soon."
        )

        LearnerControlCard(
            icon = Icons.Outlined.DeleteOutline,
            title = "Local learning data",
            description = "Mission progress will stay on this device. You will be able to review or delete it.",
            status = "Progress controls are coming soon."
        )

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
                    text = "Nothing hidden",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Text(
                    text = "ClimateQuest will explain what it saves for learning and how you can remove it.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }

    if (showRemoveCityDialog && cityToRemove != null) {
        AlertDialog(
            onDismissRequest = { showRemoveCityDialog = false },
            title = {
                Text("Remove saved city?")
            },
            text = {
                Text(
                    "$cityToRemove will be removed from this device. You can choose it again later."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearCity()
                        showRemoveCityDialog = false
                    }
                ) {
                    Text("Remove city")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showRemoveCityDialog = false }
                ) {
                    Text("Keep city")
                }
            }
        )
    }
}

@Composable
private fun LearnerControlCard(
    icon: ImageVector,
    title: String,
    description: String,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = status,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1100)
@Composable
private fun SettingsScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        SettingsScreen(
            selectedCity = "Townsville",
            onChooseCity = {},
            onClearCity = {}
        )
    }
}