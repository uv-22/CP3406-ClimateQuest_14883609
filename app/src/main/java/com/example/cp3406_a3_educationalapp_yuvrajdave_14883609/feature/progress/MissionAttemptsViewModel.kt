package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MissionAttemptsViewModel @Inject constructor(
    private val missionAttemptRepository: MissionAttemptRepository
) : ViewModel() {

    fun recordAttempt(
        missionId: String,
        wasCorrect: Boolean
    ) {
        viewModelScope.launch {
            missionAttemptRepository.saveAttempt(
                missionId = missionId,
                wasCorrect = wasCorrect
            )
        }
    }
}