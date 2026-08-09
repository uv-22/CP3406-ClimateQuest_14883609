package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather

data class WeatherSnapshot(
    val city: String,
    val temperatureCelsius: Double,
    val windSpeedKilometresPerHour: Double,
    val precipitationProbability: Int?
)