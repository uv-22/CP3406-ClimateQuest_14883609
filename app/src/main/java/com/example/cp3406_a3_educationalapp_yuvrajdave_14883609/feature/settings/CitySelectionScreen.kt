package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme

private data class CityOption(
    val name: String,
    val region: String,
    val testTag: String
)

private val cityOptions = listOf(
    CityOption("Townsville", "Queensland, Australia", "city_option_townsville"),
    CityOption("Cairns", "Queensland, Australia", "city_option_cairns"),
    CityOption("Brisbane", "Queensland, Australia", "city_option_brisbane"),
    CityOption("Hobart", "Tasmania, Australia", "city_option_hobart")
)

@Composable
fun CitySelectionScreen(
    selectedCity: String?,
    onCitySelected: (String) -> Unit,
    onNavigateBack: () -> Unit,
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
            text = "CITY CHOICE",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Choose a city manually",
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = "ClimateQuest never asks for GPS. Pick a city yourself to use in future weather missions.",
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
                    text = "Your choice, not tracking",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Your city choice is saved only on this device. You can change it any time in Settings.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Text(
            text = "Pick a city",
            style = MaterialTheme.typography.titleLarge
        )

        cityOptions.forEach { city ->
            OutlinedButton(
                onClick = { onCitySelected(city.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(city.testTag)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = city.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = city.region,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (city.name == selectedCity) {
                        Text(
                            text = "Selected for this session",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to settings")
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun CitySelectionScreenPreview() {
    CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
        CitySelectionScreen(
            selectedCity = "Townsville",
            onCitySelected = {},
            onNavigateBack = {}
        )
    }
}