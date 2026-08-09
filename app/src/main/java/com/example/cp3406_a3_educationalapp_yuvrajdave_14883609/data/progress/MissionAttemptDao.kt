package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MissionAttemptDao {

    @Insert
    suspend fun insert(attempt: MissionAttemptEntity)

    @Query(
        "SELECT * FROM mission_attempts " +
                "ORDER BY completed_at_epoch_millis DESC"
    )
    fun observeAllAttempts(): Flow<List<MissionAttemptEntity>>

    @Query("SELECT COUNT(*) FROM mission_attempts")
    fun observeTotalAttempts(): Flow<Int>

    @Query(
        "SELECT COUNT(*) FROM mission_attempts " +
                "WHERE was_correct = 1"
    )
    fun observeCorrectAttempts(): Flow<Int>
}