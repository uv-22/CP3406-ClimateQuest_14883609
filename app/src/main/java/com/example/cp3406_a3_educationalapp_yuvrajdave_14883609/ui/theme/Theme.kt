package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = QuestGreen,
    onPrimary = QuestOnGreen,
    primaryContainer = QuestGreenContainer,
    onPrimaryContainer = QuestOnGreenContainer,
    secondary = QuestSky,
    onSecondary = QuestOnSky,
    secondaryContainer = QuestSkyContainer,
    onSecondaryContainer = QuestOnSkyContainer,
    tertiary = QuestSun,
    onTertiary = QuestOnSun,
    tertiaryContainer = QuestSunContainer,
    onTertiaryContainer = QuestOnSunContainer,
    background = QuestBackground,
    onBackground = QuestOnBackground,
    surface = QuestBackground,
    onSurface = QuestOnBackground,
    surfaceVariant = QuestSurfaceVariant,
    onSurfaceVariant = QuestOnSurfaceVariant,
    outline = QuestOutline
)

private val DarkColorScheme = darkColorScheme(
    primary = QuestDarkGreen,
    onPrimary = QuestDarkOnGreen,
    primaryContainer = QuestDarkGreenContainer,
    onPrimaryContainer = QuestGreenContainer,
    secondary = QuestDarkSky,
    onSecondary = QuestDarkOnSky,
    secondaryContainer = QuestDarkSkyContainer,
    onSecondaryContainer = QuestSkyContainer,
    tertiary = QuestDarkSun,
    onTertiary = QuestDarkOnSun,
    tertiaryContainer = QuestDarkSunContainer,
    onTertiaryContainer = QuestSunContainer,
    background = QuestDarkBackground,
    onBackground = QuestDarkOnBackground,
    surface = QuestDarkBackground,
    onSurface = QuestDarkOnBackground,
    surfaceVariant = QuestDarkSurfaceVariant,
    onSurfaceVariant = QuestDarkOnSurfaceVariant,
    outline = QuestDarkOutline
)

@Composable
fun CP3406_A3EducationalApp_YuvrajDave_14883609Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}