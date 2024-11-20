package com.ddanddan.watch.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.ddanddan.ddanddan.R
import com.ddanddan.watch.presentation.service.PassiveDataService
import com.ddanddan.watch.presentation.theme.DDanDDanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startPassiveDataService()

        setContent {
            DDanDDanTheme(darkTheme = true) {
                MyApp()
            }
        }
    }

    private fun startPassiveDataService() {
        val serviceIntent = Intent(this, PassiveDataService::class.java)
        startService(serviceIntent)
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val permission = Manifest.permission.ACTIVITY_RECOGNITION //manifest에 미추가 시 요청되지 않음
    val permissionGrantedState = rememberPermissionState(context, permission)

    MonitorPermissionStatus(context, permission, permissionGrantedState)

    RenderUI(
        permissionGrantedState = permissionGrantedState,
        onGoToSettings = { openAppSettings(context) }
    )
}

/** 권한 상태 관리 **/
@Composable
fun rememberPermissionState(context: Context, permission: String): MutableState<Boolean> {
    return remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }
}

/** 설정 화면 이동 후 권한 상태 모니터링 **/
@Composable
fun MonitorPermissionStatus(context: Context, permission: String, permissionGrantedState: MutableState<Boolean>) {
    LaunchedEffect(Unit) {
        snapshotFlow {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }.collect { granted ->
            permissionGrantedState.value = granted
        }
    }
}

@Composable
fun RenderUI(
    permissionGrantedState: MutableState<Boolean>,
    onGoToSettings: () -> Unit
) {
    when {
        // 권한이 이미 허용된 경우
        permissionGrantedState.value -> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = DDanDDanColorPalette.current.color_background
            ) {
                PassiveDataApp()
            }
        }

        // 권한이 허용되지 않은 경우
        else -> {
            PermissionScreen(onGoToSettings)
        }
    }
}

/** 권한 설정 열기 **/
fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

/** 권한 요청 화면 **/
@Composable
fun PermissionScreen(onGoToSettings: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.need_physical_permission_message),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PermissionChip(onClick = onGoToSettings)
        }
    }
}

/** 설정 이동 버튼 (Chip 스타일) **/
@Composable
fun PermissionChip(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(),
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colors.primary,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.open_setting_page),
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

