package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.missions

import androidx.lifecycle.ViewModel
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.mission.Mission
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.mission.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class MissionsViewModel @Inject constructor(
    missionRepository: MissionRepository
) : ViewModel() {

    private val _missions = MutableStateFlow(missionRepository.getMissions())

    val missions: StateFlow<List<Mission>> = _missions.asStateFlow()
}