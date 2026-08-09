package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.city

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.cityPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "climatequest_settings"
)

@Singleton
class DataStoreCityPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : CityPreferencesRepository {

    override val selectedCity: Flow<String?> = context.cityPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[SELECTED_CITY]
        }

    override suspend fun saveSelectedCity(city: String) {
        context.cityPreferencesDataStore.edit { preferences ->
            preferences[SELECTED_CITY] = city
        }
    }

    override suspend fun clearSelectedCity() {
        context.cityPreferencesDataStore.edit { preferences ->
            preferences.remove(SELECTED_CITY)
        }
    }

    private companion object {
        val SELECTED_CITY = stringPreferencesKey("selected_city")
    }
}