package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.ClimateQuestDatabase
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.CachedWeatherSnapshotDao
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.CachedWeatherSnapshotEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CachedWeatherSnapshotDaoTest {

    private lateinit var database: ClimateQuestDatabase
    private lateinit var cachedWeatherSnapshotDao: CachedWeatherSnapshotDao

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(
            context,
            ClimateQuestDatabase::class.java
        ).allowMainThreadQueries().build()

        cachedWeatherSnapshotDao = database.cachedWeatherSnapshotDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun savingTheSameCityReplacesItsOlderSnapshot() = runBlocking {
        cachedWeatherSnapshotDao.save(
            weatherSnapshot(
                city = "Townsville",
                temperature = 24.0,
                fetchedAt = 100
            )
        )

        cachedWeatherSnapshotDao.save(
            weatherSnapshot(
                city = "Townsville",
                temperature = 29.0,
                fetchedAt = 200
            )
        )

        val savedSnapshot =
            cachedWeatherSnapshotDao.getSnapshotForCity("Townsville")

        assertEquals(29.0, savedSnapshot?.temperatureCelsius)
        assertEquals(200L, savedSnapshot?.fetchedAtEpochMillis)
    }

    @Test
    fun deletingCachedSnapshotsRemovesAllCities() = runBlocking {
        cachedWeatherSnapshotDao.save(
            weatherSnapshot(
                city = "Cairns",
                temperature = 27.0,
                fetchedAt = 300
            )
        )

        cachedWeatherSnapshotDao.save(
            weatherSnapshot(
                city = "Hobart",
                temperature = 17.0,
                fetchedAt = 400
            )
        )

        cachedWeatherSnapshotDao.deleteAllSnapshots()

        assertNull(cachedWeatherSnapshotDao.getSnapshotForCity("Cairns"))
        assertNull(cachedWeatherSnapshotDao.getSnapshotForCity("Hobart"))
    }

    private fun weatherSnapshot(
        city: String,
        temperature: Double,
        fetchedAt: Long
    ) = CachedWeatherSnapshotEntity(
        city = city,
        temperatureCelsius = temperature,
        windSpeedKilometresPerHour = 18.0,
        maximumRainProbabilityToday = 60,
        fetchedAtEpochMillis = fetchedAt
    )
}