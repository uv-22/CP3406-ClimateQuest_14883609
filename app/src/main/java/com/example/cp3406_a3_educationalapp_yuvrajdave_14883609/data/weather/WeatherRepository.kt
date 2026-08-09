package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

interface WeatherRepository {
    suspend fun fetchWeather(city: String): WeatherSnapshot
}

@Singleton
class OpenMeteoWeatherRepository @Inject constructor() : WeatherRepository {

    override suspend fun fetchWeather(city: String): WeatherSnapshot {
        val coordinates = cityCoordinates(city)
            ?: throw IllegalArgumentException("ClimateQuest does not have weather coordinates for $city.")

        return withContext(Dispatchers.IO) {
            val requestUrl =
                "https://api.open-meteo.com/v1/forecast" +
                        "?latitude=${coordinates.latitude}" +
                        "&longitude=${coordinates.longitude}" +
                        "&current=temperature_2m,wind_speed_10m" +
                        "&hourly=precipitation_probability" +
                        "&forecast_days=1" +
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

                val weatherJson = JSONObject(response)
                val current = weatherJson.getJSONObject("current")
                val hourly = weatherJson.optJSONObject("hourly")
                val probabilities = hourly?.optJSONArray("precipitation_probability")

                WeatherSnapshot(
                    city = city,
                    temperatureCelsius = current.getDouble("temperature_2m"),
                    windSpeedKilometresPerHour = current.getDouble("wind_speed_10m"),
                    precipitationProbability = probabilities?.optInt(0)
                )
            } finally {
                connection.disconnect()
            }
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