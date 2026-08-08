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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.home.HomeScreen

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
fun ClimateQuestApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                ClimateQuestDestination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigateToTopLevel(destination)
                        },
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
                        navController.navigateToTopLevel(ClimateQuestDestination.Missions)
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
                NavigationPlaceholderScreen(
                    title = "Settings",
                    description = "You will control cities, preferences, and locally stored learning data here."
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