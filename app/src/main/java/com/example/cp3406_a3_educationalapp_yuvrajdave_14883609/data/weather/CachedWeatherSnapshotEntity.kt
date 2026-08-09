package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_weather_snapshots")
data class CachedWeatherSnapshotEntity(
    @PrimaryKey
    val city: String,
    @ColumnInfo(name = "temperature_celsius")
    val temperatureCelsius: Double,
    @ColumnInfo(name = "wind_speed_kilometres_per_hour")
    val windSpeedKilometresPerHour: Double,
    @ColumnInfo(name = "maximum_rain_probability_today")
    val maximumRainProbabilityToday: Int?,
    @ColumnInfo(name = "fetched_at_epoch_millis")
    val fetchedAtEpochMillis: Long
)

fun CachedWeatherSnapshotEntity.toWeatherSnapshot(): WeatherSnapshot {
    return WeatherSnapshot(
        city = city,
        temperatureCelsius = temperatureCelsius,
        windSpeedKilometresPerHour = windSpeedKilometresPerHour,
        maximumRainProbabilityToday = maximumRainProbabilityToday,
        fetchedAtEpochMillis = fetchedAtEpochMillis,
        isCached = true
    )
}

fun WeatherSnapshot.toCachedWeatherSnapshotEntity(): CachedWeatherSnapshotEntity {
    return CachedWeatherSnapshotEntity(
        city = city,
        temperatureCelsius = temperatureCelsius,
        windSpeedKilometresPerHour = windSpeedKilometresPerHour,
        maximumRainProbabilityToday = maximumRainProbabilityToday,
        fetchedAtEpochMillis = fetchedAtEpochMillis
    )
}