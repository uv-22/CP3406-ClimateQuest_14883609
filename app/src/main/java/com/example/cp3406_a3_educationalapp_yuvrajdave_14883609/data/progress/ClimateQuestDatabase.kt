package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.CachedWeatherSnapshotDao
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.CachedWeatherSnapshotEntity

@Database(
    entities = [
        MissionAttemptEntity::class,
        CachedWeatherSnapshotEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class ClimateQuestDatabase : RoomDatabase() {

    abstract fun missionAttemptDao(): MissionAttemptDao

    abstract fun cachedWeatherSnapshotDao(): CachedWeatherSnapshotDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `cached_weather_snapshots` (
                        `city` TEXT NOT NULL,
                        `temperature_celsius` REAL NOT NULL,
                        `wind_speed_kilometres_per_hour` REAL NOT NULL,
                        `maximum_rain_probability_today` INTEGER,
                        `fetched_at_epoch_millis` INTEGER NOT NULL,
                        PRIMARY KEY(`city`)
                    )
                    """.trimIndent()
                )
            }
        }
    }
}