package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings.CitySelectionScreen
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme.CP3406_A3EducationalApp_YuvrajDave_14883609Theme
import org.junit.Rule
import org.junit.Test

class CitySelectionScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectedCityIsClearlyDescribedAsSavedOnThisDevice() {
        composeTestRule.setContent {
            CP3406_A3EducationalApp_YuvrajDave_14883609Theme {
                CitySelectionScreen(
                    selectedCity = "Townsville",
                    onCitySelected = {},
                    onNavigateBack = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Townsville")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Saved on this device")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Selected for this session")
            .assertDoesNotExist()
    }
}