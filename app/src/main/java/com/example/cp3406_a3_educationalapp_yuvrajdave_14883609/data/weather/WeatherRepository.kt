package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface WeatherRepository {
    suspend fun fetchWeather(city: String): WeatherSnapshot

    suspend fun loadCachedWeather(city: String): WeatherSnapshot?

    suspend fun clearCachedWeather()
}

@Singleton
class OpenMeteoWeatherRepository @Inject constructor(
    private val cachedWeatherSnapshotDao: CachedWeatherSnapshotDao
) : WeatherRepository {

    override suspend fun fetchWeather(city: String): WeatherSnapshot {
        val coordinates = cityCoordinates(city)
            ?: throw IllegalArgumentException(
                "ClimateQuest does not have weather coordinates for $city."
            )

        return withContext(Dispatchers.IO) {
            val requestUrl =
                "https://api.open-meteo.com/v1/forecast" +
                        "?latitude=${coordinates.latitude}" +
                        "&longitude=${coordinates.longitude}" +
                        "&current=temperature_2m,wind_speed_10m" +
                        "&daily=precipitation_probability_max" +
                        "&forecast_days=1" +
                        "&timezone=auto" +
                        "&wind_speed_unit=kmh"

            val connection = URL(requestUrl).openConnection() as HttpURLConnection

            try {
                connection.requestMethod = "GET"
                connection.connectTimeout = 10_000
                connection.readTimeout = 10_000

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    throw IOException(
                        "Weather service returned HTTP ${connection.responseCode}."
                    )
                }

                val response = connection.inputStream
                    .bufferedReader()
                    .use { reader -> reader.readText() }

                val weatherSnapshot = WeatherResponseParser.parse(
                    city = city,
                    response = response
                ).copy(
                    fetchedAtEpochMillis = System.currentTimeMillis(),
                    isCached = false
                )

                try {
                    cachedWeatherSnapshotDao.save(
                        weatherSnapshot.toCachedWeatherSnapshotEntity()
                    )
                } catch (exception: CancellationException) {
                    throw exception
                } catch (_: Exception) {
                    // Fresh conditions remain useful even if a local cache write fails.
                }

                weatherSnapshot
            } finally {
                connection.disconnect()
            }
        }
    }

    override suspend fun loadCachedWeather(city: String): WeatherSnapshot? {
        return withContext(Dispatchers.IO) {
            cachedWeatherSnapshotDao
                .getSnapshotForCity(city)
                ?.toWeatherSnapshot()
        }
    }

    override suspend fun clearCachedWeather() {
        withContext(Dispatchers.IO) {
            cachedWeatherSnapshotDao.deleteAllSnapshots()
        }
    }

    private fun cityCoordinates(city: String): CityCoordinates? {
        return when (city) {
            "Townsville" -> CityCoordinates(-19.2589, 146.8169)
            "Cairns" -> CityCoordinates(-16.9186, 145.7781)
            "Brisbane" -> CityCoordinates(-27.4698, 153.0251)
            "Hobart" -> CityCoordinates(-42.8821, 147.3272)
            else -> null
        }
    }

    private data class CityCoordinates(
        val latitude: Double,
        val longitude: Double
    )
}