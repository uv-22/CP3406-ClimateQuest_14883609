package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.city.CityPreferencesRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherRepository
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.weather.WeatherSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    init {
        viewModelScope.launch {
            cityPreferencesRepository.selectedCity.collectLatest { city ->
                selectedCity = city

                if (city == null) {
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

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading(city)

            _weatherUiState.value = try {
                WeatherUiState.Success(
                    weatherSnapshot = weatherRepository.fetchWeather(city)
                )
            } catch (_: Exception) {
                WeatherUiState.Error(city)
            }
        }
    }
}