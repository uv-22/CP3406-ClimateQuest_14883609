package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptEntity
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.progress.ProgressScreen
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.progress.ProgressUiState
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme
import org.junit.Rule
import org.junit.Test

class ProgressScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun emptyProgressShowsMissionPrompt() {
        composeTestRule.setContent {
            var startMissionRequested by remember { mutableStateOf(false) }

            CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
                ProgressScreen(
                    progressUiState = ProgressUiState(),
                    onStartMission = {
                        startMissionRequested = true
                    }
                )

                if (startMissionRequested) {
                    Text("Mission start requested for test")
                }
            }
        }

        composeTestRule.onNodeWithText("Ready for your first mission?")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Explore missions").performClick()
        composeTestRule.onNodeWithText("Mission start requested for test")
            .assertIsDisplayed()
    }

    @Test
    fun savedProgressShowsStatisticsAndRecentActivity() {
        composeTestRule.setContent {
            CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
                ProgressScreen(
                    progressUiState = ProgressUiState(
                        totalAttempts = 3,
                        correctAttempts = 2,
                        completedMissionIds = setOf(
                            "forecast_detective",
                            "weather_or_climate"
                        ),
                        recentAttempts = listOf(
                            MissionAttemptEntity(
                                id = 1,
                                missionId = "weather_or_climate",
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

        composeTestRule.onNodeWithText("Responses saved")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("3")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Evidence-based answers")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("2 of 3")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Missions completed")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Recent activity")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Forecast detective")
            .assertIsDisplayed()
    }
}