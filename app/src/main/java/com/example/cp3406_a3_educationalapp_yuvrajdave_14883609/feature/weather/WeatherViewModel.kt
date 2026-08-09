package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.city.CityPreferencesRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed interface WeatherUiState {
    data object NoCityChosen : WeatherUiState
    data class Loading(val city: String) : WeatherUiState
    data class Success(val weatherSnapshot: WeatherSnapshot) : WeatherUiState
    data class Error(val city: String) : WeatherUiState
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val cityPreferencesRepository: CityPreferencesRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherUiState = MutableStateFlow<WeatherUiState>(
        WeatherUiState.NoCityChosen
    )

    val weatherUiState: StateFlow<WeatherUiState> =
        _weatherUiState.asStateFlow()

    private var selectedCity: String? = null
    private var weatherLoadJob: Job? = null
    private var weatherRequestVersion = 0

    init {
        viewModelScope.launch {
            cityPreferencesRepository.selectedCity.collect { city ->
                selectedCity = city

                if (city == null) {
                    cancelWeatherLoad()
                    _weatherUiState.value = WeatherUiState.NoCityChosen
                } else {
                    loadWeather(city)
                }
            }
        }
    }

    fun refreshWeather() {
        selectedCity?.let { city ->
            loadWeather(city)
        }
    }

    fun clearCachedWeather() {
        viewModelScope.launch {
            try {
                weatherRepository.clearCachedWeather()
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                // Clearing local cache must not crash the app.
            }
        }
    }

    private fun loadWeather(city: String) {
        val requestVersion = ++weatherRequestVersion

        weatherLoadJob?.cancel()
        weatherLoadJob = viewModelScope.launch {
            if (selectedCity != city || requestVersion != weatherRequestVersion) {
                return@launch
            }

            _weatherUiState.value = WeatherUiState.Loading(city)

            try {
                val weatherSnapshot = weatherRepository.fetchWeather(city)

                if (selectedCity == city && requestVersion == weatherRequestVersion) {
                    _weatherUiState.value = WeatherUiState.Success(weatherSnapshot)
                }
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                val cachedWeatherSnapshot = try {
                    weatherRepository.loadCachedWeather(city)
                } catch (cacheException: CancellationException) {
                    throw cacheException
                } catch (_: Exception) {
                    null
                }

                if (selectedCity == city && requestVersion == weatherRequestVersion) {
                    _weatherUiState.value = cachedWeatherSnapshot?.let {
                        WeatherUiState.Success(it)
                    } ?: WeatherUiState.Error(city)
                }
            }
        }
    }

    private fun cancelWeatherLoad() {
        weatherRequestVersion += 1
        weatherLoadJob?.cancel()
        weatherLoadJob = null
    }

    override fun onCleared() {
        cancelWeatherLoad()
        super.onCleared()
    }
}