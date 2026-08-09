package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.home.HomeScreen
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings.CitySelectionScreen
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings.CitySettingsViewModel
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings.SettingsScreen

private const val CITY_SELECTION_ROUTE = "city_selection"

enum class ClimateQuestDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    Home("home", "Home", Icons.Outlined.Home),
    Missions("missions", "Missions", Icons.Outlined.PlayArrow),
    Progress("progress", "Progress", Icons.Outlined.BarChart),
    Settings("settings", "Settings", Icons.Outlined.Settings)
}

@Composable
fun ClimateQuestApp(
    citySettingsViewModel: CitySettingsViewModel = viewModel()
) {
    val navController = rememberNavController()
    val selectedCity by citySettingsViewModel.selectedCity.collectAsStateWithLifecycle()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                ClimateQuestDestination.entries.forEach { destination ->
                    val isSelected = currentRoute == destination.route ||
                            (
                                    destination == ClimateQuestDestination.Settings &&
                                            currentRoute == CITY_SELECTION_ROUTE
                                    )

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigateToTopLevel(destination)
                        },
                        modifier = Modifier.testTag("navigation_${destination.route}"),
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = null
                            )
                        },
                        label = { Text(destination.label) },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ClimateQuestDestination.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ClimateQuestDestination.Home.route) {
                HomeScreen(
                    onStartMission = {
                        navController.navigateToTopLevel(
                            ClimateQuestDestination.Missions
                        )
                    }
                )
            }

            composable(ClimateQuestDestination.Missions.route) {
                NavigationPlaceholderScreen(
                    title = "Missions",
                    description = "Short learning missions will help you practise interpreting forecast evidence."
                )
            }

            composable(ClimateQuestDestination.Progress.route) {
                NavigationPlaceholderScreen(
                    title = "Progress",
                    description = "Your completed missions and learning growth will appear here."
                )
            }

            composable(ClimateQuestDestination.Settings.route) {
                SettingsScreen(
                    selectedCity = selectedCity,
                    onChooseCity = {
                        navController.navigate(CITY_SELECTION_ROUTE)
                    }
                )
            }

            composable(CITY_SELECTION_ROUTE) {
                CitySelectionScreen(
                    selectedCity = selectedCity,
                    onCitySelected = { city ->
                        citySettingsViewModel.selectCity(city)
                        navController.popBackStack()
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
private fun NavigationPlaceholderScreen(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Foundation ready",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "This screen will be developed in the next focused feature commits.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

private fun NavHostController.navigateToTopLevel(
    destination: ClimateQuestDestination
) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}