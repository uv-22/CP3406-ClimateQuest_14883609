package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mission_attempts")
data class MissionAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "mission_id")
    val missionId: String,
    @ColumnInfo(name = "was_correct")
    val wasCorrect: Boolean,
    @ColumnInfo(name = "completed_at_epoch_millis")
    val completedAtEpochMillis: Long
)