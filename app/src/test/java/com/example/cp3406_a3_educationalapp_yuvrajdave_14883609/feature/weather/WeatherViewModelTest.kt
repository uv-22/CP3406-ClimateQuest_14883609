package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.weather

import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.city.CityPreferencesRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherSnapshot
import java.io.IOException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun noCityChosen_doesNotRequestWeather() =
        runTest(mainDispatcherRule.testDispatcher) {
            val cityRepository = FakeCityPreferencesRepository(initialCity = null)
            val weatherRepository = RecordingWeatherRepository { city, _ ->
                weatherSnapshot(city)
            }

            val viewModel = WeatherViewModel(
                cityPreferencesRepository = cityRepository,
                weatherRepository = weatherRepository
            )

            advanceUntilIdle()
            viewModel.refreshWeather()
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.NoCityChosen,
                viewModel.weatherUiState.value
            )
            assertTrue(weatherRepository.requestedCities.isEmpty())
        }

    @Test
    fun refreshWeather_retriesAfterAnErrorAndShowsTheNewResult() =
        runTest(mainDispatcherRule.testDispatcher) {
            val cityRepository = FakeCityPreferencesRepository(initialCity = null)
            val weatherRepository = RecordingWeatherRepository { city, requestNumber ->
                if (requestNumber == 1) {
                    throw IOException("Weather service is unavailable")
                }

                weatherSnapshot(city)
            }

            val viewModel = WeatherViewModel(
                cityPreferencesRepository = cityRepository,
                weatherRepository = weatherRepository
            )

            cityRepository.emitCity("Cairns")
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.Error(city = "Cairns"),
                viewModel.weatherUiState.value
            )

            viewModel.refreshWeather()
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.Success(weatherSnapshot("Cairns")),
                viewModel.weatherUiState.value
            )
            assertEquals(
                listOf("Cairns", "Cairns"),
                weatherRepository.requestedCities
            )
        }

    @Test
    fun failedWeatherRequest_usesSavedConditionsForTheSameCity() =
        runTest(mainDispatcherRule.testDispatcher) {
            val savedSnapshot = weatherSnapshot("Cairns").copy(
                fetchedAtEpochMillis = 1_000L,
                isCached = true
            )

            val cityRepository = FakeCityPreferencesRepository(initialCity = null)
            val weatherRepository = RecordingWeatherRepository(
                cachedWeatherForCity = { city ->
                    savedSnapshot.takeIf { it.city == city }
                }
            ) { _, _ ->
                throw IOException("Weather service is unavailable")
            }

            val viewModel = WeatherViewModel(
                cityPreferencesRepository = cityRepository,
                weatherRepository = weatherRepository
            )

            cityRepository.emitCity("Cairns")
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.Success(savedSnapshot),
                viewModel.weatherUiState.value
            )
            assertEquals(
                listOf("Cairns"),
                weatherRepository.requestedCities
            )
        }

    @Test
    fun switchingCities_ignoresALateResultForThePreviouslySelectedCity() =
        runTest(mainDispatcherRule.testDispatcher) {
            val cityRepository = FakeCityPreferencesRepository(initialCity = null)
            val weatherRepository = ControlledWeatherRepository()

            val viewModel = WeatherViewModel(
                cityPreferencesRepository = cityRepository,
                weatherRepository = weatherRepository
            )

            cityRepository.emitCity("Townsville")
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.Loading(city = "Townsville"),
                viewModel.weatherUiState.value
            )

            cityRepository.emitCity("Hobart")
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.Loading(city = "Hobart"),
                viewModel.weatherUiState.value
            )

            weatherRepository.complete(
                city = "Townsville",
                snapshot = weatherSnapshot("Townsville")
            )
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.Loading(city = "Hobart"),
                viewModel.weatherUiState.value
            )

            weatherRepository.complete(
                city = "Hobart",
                snapshot = weatherSnapshot("Hobart")
            )
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.Success(weatherSnapshot("Hobart")),
                viewModel.weatherUiState.value
            )
        }

    @Test
    fun clearingCity_ignoresALateWeatherResult() =
        runTest(mainDispatcherRule.testDispatcher) {
            val cityRepository = FakeCityPreferencesRepository(initialCity = null)
            val weatherRepository = ControlledWeatherRepository()

            val viewModel = WeatherViewModel(
                cityPreferencesRepository = cityRepository,
                weatherRepository = weatherRepository
            )

            cityRepository.emitCity("Townsville")
            advanceUntilIdle()

            cityRepository.emitCity(null)
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.NoCityChosen,
                viewModel.weatherUiState.value
            )

            weatherRepository.complete(
                city = "Townsville",
                snapshot = weatherSnapshot("Townsville")
            )
            advanceUntilIdle()

            assertEquals(
                WeatherUiState.NoCityChosen,
                viewModel.weatherUiState.value
            )
        }

    private fun weatherSnapshot(city: String) = WeatherSnapshot(
        city = city,
        temperatureCelsius = 24.5,
        windSpeedKilometresPerHour = 18.0,
        maximumRainProbabilityToday = 60
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

private class FakeCityPreferencesRepository(
    initialCity: String?
) : CityPreferencesRepository {

    private val selectedCityState = MutableStateFlow(initialCity)

    override val selectedCity: Flow<String?> = selectedCityState

    fun emitCity(city: String?) {
        selectedCityState.value = city
    }

    override suspend fun saveSelectedCity(city: String) {
        selectedCityState.value = city
    }

    override suspend fun clearSelectedCity() {
        selectedCityState.value = null
    }
}

private class RecordingWeatherRepository(
    private val cachedWeatherForCity: suspend (city: String) -> WeatherSnapshot? = {
        null
    },
    private val responseForRequest: suspend (
        city: String,
        requestNumber: Int
    ) -> WeatherSnapshot
) : WeatherRepository {

    val requestedCities = mutableListOf<String>()

    override suspend fun fetchWeather(city: String): WeatherSnapshot {
        requestedCities += city

        return responseForRequest(
            city,
            requestedCities.size
        )
    }

    override suspend fun loadCachedWeather(city: String): WeatherSnapshot? {
        return cachedWeatherForCity(city)
    }

    override suspend fun clearCachedWeather() = Unit
}

private class ControlledWeatherRepository : WeatherRepository {

    private val pendingRequests =
        mutableMapOf<String, Continuation<WeatherSnapshot>>()

    override suspend fun fetchWeather(city: String): WeatherSnapshot =
        suspendCoroutine { continuation ->
            pendingRequests[city] = continuation
        }

    override suspend fun loadCachedWeather(city: String): WeatherSnapshot? = null

    override suspend fun clearCachedWeather() = Unit

    fun complete(
        city: String,
        snapshot: WeatherSnapshot
    ) {
        val continuation = pendingRequests.remove(city)
            ?: error("No pending weather request for $city.")

        continuation.resume(snapshot)
    }
}