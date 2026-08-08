package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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
        composeTestRule.onNodeWithText("Your learning controls").assertIsDisplayed()
        composeTestRule.onNodeWithText("Local learning data")
            .performScrollTo()
            .assertIsDisplayed()
    }
}
