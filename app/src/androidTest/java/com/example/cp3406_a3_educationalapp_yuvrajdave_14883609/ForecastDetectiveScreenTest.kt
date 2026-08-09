package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherSnapshot
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.missions.ForecastDetectiveScreen
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.weather.WeatherUiState
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme
import org.junit.Rule
import org.junit.Test

class ForecastDetectiveScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun liveWeatherShowsTheHighestRainChanceForToday() {
        composeTestRule.setContent {
            CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
                ForecastDetectiveScreen(
                    weatherUiState = WeatherUiState.Success(
                        WeatherSnapshot(
                            city = "Townsville",
                            temperatureCelsius = 28.0,
                            windSpeedKilometresPerHour = 18.0,
                            maximumRainProbabilityToday = 65
                        )
                    ),
                    onRefreshWeather = {},
                    onNavigateBack = {},
                    onAttemptRecorded = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Highest chance of rain today: 65%")
            .performScrollTo()
            .assertIsDisplayed()
    }
}