package com.ddanddan.watch.presentation

import android.Manifest
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
import com.ddanddan.watch.presentation.theme.PassiveDataTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PassiveDataApp(viewModel: PassiveDataViewModel = hiltViewModel()) {
    val permissionState = rememberPermissionState(Manifest.permission.ACTIVITY_RECOGNITION)

    val uiState by viewModel.uiState.collectAsState()
    val calories by viewModel.caloriesValue.collectAsState()
    val user by viewModel.userState.collectAsState()
    val mainPet by viewModel.mainPetState.collectAsState()
    val goalCalories = user?.purposeCalorie?.toDouble() ?: 1000.0 // 임시 값

    PassiveDataTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            // 권한이 허용되지 않았을 경우 권한 요청 UI 표시
            if (!permissionState.status.isGranted) {
                PermissionScreen(
                    onRequestPermission = {
                        permissionState.launchPermissionRequest()
                    }
                )
            } else {
                // 권한이 허용된 경우 기존 UI 표시
                when (uiState) {
                    UiState.Startup -> {
                        LoadingScreen()
                    }

                    UiState.Supported -> {
                        if (user != null && mainPet != null) {
                            CalorieProgressBar(
                                calories = calories,
                                goalCalories = goalCalories,
                                mainPet = mainPet!!,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            LoadingScreen()
                        }
                    }

                    UiState.NotSupported -> {
                        NotSupportedScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
