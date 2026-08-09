package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.city

import kotlinx.coroutines.flow.Flow

interface CityPreferencesRepository {
    val selectedCity: Flow<String?>

    suspend fun saveSelectedCity(city: String)

    suspend fun clearSelectedCity()
}