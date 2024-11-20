package com.ddanddan.watch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddanddan.watch.domain.model.MainPet
import com.ddanddan.watch.domain.model.User
import com.ddanddan.watch.domain.repository.DdanDdanRepository
import com.ddanddan.watch.domain.repository.HealthServicesRepository
import com.ddanddan.watch.domain.repository.PassiveDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PassiveDataViewModel @Inject constructor(
    private val healthServicesRepository: HealthServicesRepository,
    private val passiveDataRepository: PassiveDataRepository,
    private val ddanDdanRepository: DdanDdanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Startup)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    private val _mainPetState = MutableStateFlow<MainPet?>(null)
    val mainPetState: StateFlow<MainPet?> = _mainPetState.asStateFlow()

    val caloriesValue = passiveDataRepository.latestCalories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Double.NaN)

    init {
        viewModelScope.launch {
            checkCalorieSupportAndRegister()
        }

        viewModelScope.launch {
            loadUserAndPetData()
                .onStart { _uiState.value = UiState.Startup } // 시작 상태
                .catch { e -> // 예외 처리
                    Timber.e("Failed to load user or pet data: ${e.message}")
                    _uiState.value = UiState.NotSupported
                }
                .collect { result -> // 성공 시 데이터 반영
                    _userState.value = result.first
                    _mainPetState.value = result.second
                    _uiState.value = UiState.Supported
                }
        }
    }

    private fun loadUserAndPetData(): Flow<Pair<User, MainPet>> {
        val userFlow = flow { emit(ddanDdanRepository.getUser()) }
        val mainPetFlow = flow { emit(ddanDdanRepository.getMainPet()) }

        return userFlow.zip(mainPetFlow) { user, mainPet ->
            user to mainPet
        }
    }

    private suspend fun checkCalorieSupportAndRegister() {
        val isCalorieSupported = healthServicesRepository.hasCaloriesCapability()
        if (isCalorieSupported) {
            healthServicesRepository.registerForCaloriesData()
        } else {
            _uiState.value = UiState.NotSupported
        }
    }
}


sealed class UiState {
    data object Startup : UiState()
    data object NotSupported : UiState()
    data object Supported : UiState()
}
