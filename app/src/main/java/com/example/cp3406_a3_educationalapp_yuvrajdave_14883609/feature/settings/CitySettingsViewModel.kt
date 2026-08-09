package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.city.CityPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class CitySettingsViewModel @Inject constructor(
    private val cityPreferencesRepository: CityPreferencesRepository
) : ViewModel() {

    val selectedCity: StateFlow<String?> = cityPreferencesRepository.selectedCity
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun selectCity(city: String) {
        viewModelScope.launch {
            cityPreferencesRepository.saveSelectedCity(city)
        }
    }
    fun clearSelectedCity() {
        viewModelScope.launch {
            cityPreferencesRepository.clearSelectedCity()
        }
    }
}
