package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather

import org.json.JSONObject

/**
 * Maps the specific Open-Meteo values shown by ClimateQuest into app data.
 * Keeping this separate from networking makes the weather data easier to test.
 */
object WeatherResponseParser {

    fun parse(
        city: String,
        response: String
    ): WeatherSnapshot {
        val weatherJson = JSONObject(response)
        val current = weatherJson.getJSONObject("current")
        val daily = weatherJson.optJSONObject("daily")
        val maximumRainProbabilities =
            daily?.optJSONArray("precipitation_probability_max")

        return WeatherSnapshot(
            city = city,
            temperatureCelsius = current.getDouble("temperature_2m"),
            windSpeedKilometresPerHour = current.getDouble("wind_speed_10m"),
            maximumRainProbabilityToday = maximumRainProbabilities
                ?.takeIf { probabilities ->
                    probabilities.length() > 0 && !probabilities.isNull(0)
                }
                ?.optInt(0)
        )
    }
}