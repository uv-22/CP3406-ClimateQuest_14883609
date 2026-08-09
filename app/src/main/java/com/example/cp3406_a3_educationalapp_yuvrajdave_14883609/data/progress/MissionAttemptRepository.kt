package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress

import kotlinx.coroutines.flow.Flow

interface MissionAttemptRepository {
    val attempts: Flow<List<MissionAttemptEntity>>
    val totalAttempts: Flow<Int>
    val correctAttempts: Flow<Int>

    suspend fun saveAttempt(
        missionId: String,
        wasCorrect: Boolean
    )
}

class RoomMissionAttemptRepository(
    private val missionAttemptDao: MissionAttemptDao
) : MissionAttemptRepository {

    override val attempts: Flow<List<MissionAttemptEntity>> =
        missionAttemptDao.observeAllAttempts()

    override val totalAttempts: Flow<Int> =
        missionAttemptDao.observeTotalAttempts()

    override val correctAttempts: Flow<Int> =
        missionAttemptDao.observeCorrectAttempts()

    override suspend fun saveAttempt(
        missionId: String,
        wasCorrect: Boolean
    ) {
        missionAttemptDao.insert(
            MissionAttemptEntity(
                missionId = missionId,
                wasCorrect = wasCorrect,
                completedAtEpochMillis = System.currentTimeMillis()
            )
        )
    }
}