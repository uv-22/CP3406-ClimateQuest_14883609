package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.mission.LocalMissionRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MissionRepositoryTest {

    private val missionRepository = LocalMissionRepository()

    @Test
    fun getMissions_returnsTheThreePlannedLearningMissions() {
        val missionIds = missionRepository.getMissions().map { mission ->
            mission.id
        }

        assertEquals(
            listOf(
                "forecast_detective",
                "forecast_uncertainty",
                "weather_or_climate"
            ),
            missionIds
        )
    }

    @Test
    fun getMissions_returnsUsableMissionContent() {
        val missions = missionRepository.getMissions()

        assertTrue(
            missions.all { mission ->
                mission.title.isNotBlank() &&
                        mission.description.isNotBlank() &&
                        mission.skill.isNotBlank() &&
                        mission.estimatedMinutes > 0
            }
        )
    }

    @Test
    fun getMissions_returnsUniqueMissionIds() {
        val missionIds = missionRepository.getMissions().map { mission ->
            mission.id
        }

        assertEquals(
            missionIds.size,
            missionIds.toSet().size
        )
    }
}