package com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.feature.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptEntity
import com.example.cp3406_a3_educationalapp_yuvrajdave_14883609.data.progress.MissionAttemptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProgressUiState(
    val totalAttempts: Int = 0,
    val correctAttempts: Int = 0,
    val completedMissionIds: Set<String> = emptySet(),
    val recentAttempts: List<MissionAttemptEntity> = emptyList()
)

@HiltViewModel
class MissionAttemptsViewModel @Inject constructor(
    private val missionAttemptRepository: MissionAttemptRepository
) : ViewModel() {

    val progressUiState: StateFlow<ProgressUiState> = combine(
        missionAttemptRepository.attempts,
        missionAttemptRepository.totalAttempts,
        missionAttemptRepository.correctAttempts
    ) { attempts, totalAttempts, correctAttempts ->
        ProgressUiState(
            totalAttempts = totalAttempts,
            correctAttempts = correctAttempts,
            completedMissionIds = attempts
                .filter { it.wasCorrect }
                .map { it.missionId }
                .toSet(),
            recentAttempts = attempts.take(3)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProgressUiState()
    )

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