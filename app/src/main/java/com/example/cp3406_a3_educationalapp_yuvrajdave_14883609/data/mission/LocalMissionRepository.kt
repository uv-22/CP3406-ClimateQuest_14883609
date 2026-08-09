package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.mission

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalMissionRepository @Inject constructor() : MissionRepository {

    override fun getMissions(): List<Mission> {
        return listOf(
            Mission(
                id = "forecast_detective",
                title = "Forecast detective",
                description = "Use temperature, rain chance, and wind to plan a weekend activity with evidence.",
                skill = "Reading weather evidence",
                estimatedMinutes = 6
            ),
            Mission(
                id = "forecast_uncertainty",
                title = "Forecasts are not promises",
                description = "Explore why a forecast can be useful even when the weather turns out differently.",
                skill = "Explaining uncertainty",
                estimatedMinutes = 5
            ),
            Mission(
                id = "weather_or_climate",
                title = "Weather or climate?",
                description = "Sort everyday weather observations from longer-term climate patterns.",
                skill = "Comparing time scales",
                estimatedMinutes = 7
            )
        )
    }
}