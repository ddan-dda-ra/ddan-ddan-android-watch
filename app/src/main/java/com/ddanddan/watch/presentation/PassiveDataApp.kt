package com.ddanddan.watch.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Scaffold
import com.ddanddan.watch.domain.model.MainPet
import com.ddanddan.watch.domain.model.User
import com.ddanddan.watch.presentation.theme.PassiveDataTheme

@Composable
fun PassiveDataApp(viewModel: PassiveDataViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val calories by viewModel.caloriesValue.collectAsState()
    val user by viewModel.userState.collectAsState()
    val mainPet by viewModel.mainPetState.collectAsState()

    val goalCalories = calculateGoalCalories(user)

    PassiveDataTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            RenderUI(uiState, user, mainPet, calories, goalCalories)
        }
    }
}

/** UI 렌더링 **/
@Composable
fun RenderUI(
    uiState: UiState,
    user: User?,
    mainPet: MainPet?,
    calories: Double,
    goalCalories: Double
) {
    when (uiState) {
        UiState.Startup -> LoadingScreen()
        UiState.Supported -> RenderSupportedUI(user, mainPet, calories, goalCalories)
        UiState.NotSupported -> NotSupportedScreen()
    }
}

/** Supported 상태 UI 렌더링 **/
@Composable
fun RenderSupportedUI(
    user: User?,
    mainPet: MainPet?,
    calories: Double,
    goalCalories: Double
) {
    if (user != null && mainPet != null) {
        CalorieProgressBar(
            calories = calories,
            goalCalories = goalCalories,
            mainPet = mainPet,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LoadingScreen()
    }
}

/** 목표 칼로리 계산 **/
private fun calculateGoalCalories(user: User?): Double {
    return user?.purposeCalorie?.toDouble() ?: 1000.0
}

/** 로딩 화면 **/
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
