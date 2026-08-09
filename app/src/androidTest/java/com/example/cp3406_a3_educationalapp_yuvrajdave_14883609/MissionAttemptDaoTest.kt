package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.ClimateQuestDatabase
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptDao
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MissionAttemptDaoTest {

    private lateinit var database: ClimateQuestDatabase
    private lateinit var missionAttemptDao: MissionAttemptDao

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(
            context,
            ClimateQuestDatabase::class.java
        ).allowMainThreadQueries().build()

        missionAttemptDao = database.missionAttemptDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertedAttemptsAreOrderedAndCountedCorrectly() = runBlocking {
        missionAttemptDao.insert(
            MissionAttemptEntity(
                missionId = "forecast_detective",
                wasCorrect = false,
                completedAtEpochMillis = 100
            )
        )

        missionAttemptDao.insert(
            MissionAttemptEntity(
                missionId = "weather_or_climate",
                wasCorrect = true,
                completedAtEpochMillis = 200
            )
        )

        val attempts = missionAttemptDao.observeAllAttempts().first()

        assertEquals(2, attempts.size)
        assertEquals("weather_or_climate", attempts[0].missionId)
        assertEquals("forecast_detective", attempts[1].missionId)
        assertEquals(2, missionAttemptDao.observeTotalAttempts().first())
        assertEquals(1, missionAttemptDao.observeCorrectAttempts().first())
    }

    @Test
    fun deletingAttemptsRemovesAllSavedProgressData() = runBlocking {
        missionAttemptDao.insert(
            MissionAttemptEntity(
                missionId = "forecast_uncertainty",
                wasCorrect = true,
                completedAtEpochMillis = 300
            )
        )

        missionAttemptDao.deleteAllAttempts()

        assertTrue(missionAttemptDao.observeAllAttempts().first().isEmpty())
        assertEquals(0, missionAttemptDao.observeTotalAttempts().first())
        assertEquals(0, missionAttemptDao.observeCorrectAttempts().first())
    }
}