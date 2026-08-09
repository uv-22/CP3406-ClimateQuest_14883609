package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedWeatherSnapshotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(snapshot: CachedWeatherSnapshotEntity)

    @Query(
        "SELECT * FROM cached_weather_snapshots " +
                "WHERE city = :city LIMIT 1"
    )
    suspend fun getSnapshotForCity(city: String): CachedWeatherSnapshotEntity?

    @Query("DELETE FROM cached_weather_snapshots")
    suspend fun deleteAllSnapshots()
}