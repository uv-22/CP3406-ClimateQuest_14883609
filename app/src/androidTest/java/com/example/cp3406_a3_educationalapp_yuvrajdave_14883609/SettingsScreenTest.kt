package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings.SettingsScreen
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun settingsTabShowsPrivacyAndLearnerControls() {
        composeTestRule.onNodeWithTag("navigation_settings").performClick()

        composeTestRule.onNodeWithText("Privacy at a glance").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "ClimateQuest does not use GPS, accounts, ads, analytics, or background tracking."
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your learning controls")
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Local learning data")
            .performScrollTo()
            .assertIsDisplayed()
    }
}

class SettingsCityControlsTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            var selectedCity by remember { mutableStateOf<String?>(null) }

            CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
                SettingsScreen(
                    selectedCity = selectedCity,
                    onChooseCity = {
                        selectedCity = "Townsville"
                    },
                    onClearCity = {
                        selectedCity = null
                    }
                )
            }
        }
    }

    @Test
    fun choosingCityShowsTheSelectedCity() {
        composeTestRule.onNodeWithTag("choose_city_button").performClick()

        composeTestRule.onNodeWithText("Current city: Townsville")
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("remove_city_button")
            .assertIsDisplayed()
    }

    @Test
    fun keepingCityDismissesRemovalDialogWithoutRemovingCity() {
        composeTestRule.onNodeWithTag("choose_city_button").performClick()
        composeTestRule.onNodeWithTag("remove_city_button").performClick()

        composeTestRule.onNodeWithText("Remove saved city?")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Keep city").performClick()

        composeTestRule.onNodeWithText("Current city: Townsville")
            .assertIsDisplayed()
    }

    @Test
    fun confirmingRemovalShowsNoCityChosen() {
        composeTestRule.onNodeWithTag("choose_city_button").performClick()
        composeTestRule.onNodeWithTag("remove_city_button").performClick()
        composeTestRule.onNodeWithText("Remove city").performClick()

        composeTestRule.onNodeWithText("No city chosen yet")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Choose a city")
            .assertIsDisplayed()
    }
}