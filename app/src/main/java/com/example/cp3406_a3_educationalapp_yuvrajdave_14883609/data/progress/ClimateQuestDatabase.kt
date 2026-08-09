package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MissionAttemptEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ClimateQuestDatabase : RoomDatabase() {

    abstract fun missionAttemptDao(): MissionAttemptDao
}