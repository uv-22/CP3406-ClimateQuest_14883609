package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.di

import android.content.Context
import androidx.room.Room
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.ClimateQuestDatabase
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptDao
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.RoomMissionAttemptRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProgressDatabaseModule {

    @Provides
    @Singleton
    fun provideClimateQuestDatabase(
        @ApplicationContext context: Context
    ): ClimateQuestDatabase {
        return Room.databaseBuilder(
            context,
            ClimateQuestDatabase::class.java,
            "climatequest_database"
        ).build()
    }

    @Provides
    fun provideMissionAttemptDao(
        database: ClimateQuestDatabase
    ): MissionAttemptDao {
        return database.missionAttemptDao()
    }

    @Provides
    @Singleton
    fun provideMissionAttemptRepository(
        missionAttemptDao: MissionAttemptDao
    ): MissionAttemptRepository {
        return RoomMissionAttemptRepository(missionAttemptDao)
    }
}