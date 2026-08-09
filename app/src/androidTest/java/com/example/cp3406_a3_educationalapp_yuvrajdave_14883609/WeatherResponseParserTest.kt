package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherResponseParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherResponseParserTest {

    @Test
    fun parseReadsCurrentConditionsAndHighestRainChance() {
        val response = """
            {
              "current": {
                "temperature_2m": 26.7,
                "wind_speed_10m": 18.4
              },
              "daily": {
                "precipitation_probability_max": [65]
              }
            }
        """.trimIndent()

        val snapshot = WeatherResponseParser.parse(
            city = "Cairns",
            response = response
        )

        assertEquals("Cairns", snapshot.city)
        assertEquals(26.7, snapshot.temperatureCelsius, 0.001)
        assertEquals(18.4, snapshot.windSpeedKilometresPerHour, 0.001)
        assertEquals(65, snapshot.maximumRainProbabilityToday)
    }

    @Test
    fun parseAllowsWeatherDataWithoutRainProbability() {
        val response = """
            {
              "current": {
                "temperature_2m": 21.0,
                "wind_speed_10m": 12.0
              },
              "daily": {}
            }
        """.trimIndent()

        val snapshot = WeatherResponseParser.parse(
            city = "Hobart",
            response = response
        )

        assertEquals("Hobart", snapshot.city)
        assertNull(snapshot.maximumRainProbabilityToday)
    }
}