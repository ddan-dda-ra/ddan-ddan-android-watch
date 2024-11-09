package com.ddanddan.watch.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.watch.domain.repository.HealthServicesRepository
import com.ddanddan.watch.domain.repository.PassiveDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassiveDataViewModel @Inject constructor(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository
) : ViewModel() {
    val caloriesValue = passiveDataRepository.latestCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)

    private val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    init {
        viewModelScope.launch {
            val isCalorieSupported = healthServicesRepository.hasCaloriesCapability()

            uiState.value = when {
                isCalorieSupported -> {
                    healthServicesRepository.registerForCaloriesData()
                    UiState.Supported
                }

                else -> UiState.NotSupported
            }
        }
    }
}

sealed class UiState {
    data object Startup : UiState()
    data object NotSupported : UiState()
    data object Supported : UiState()
}
