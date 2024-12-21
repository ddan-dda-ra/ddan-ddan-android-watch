package com.ddanddan.watch.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.ddanddan.ddanddan.R
import com.ddanddan.watch.presentation.service.PassiveDataService
import com.ddanddan.watch.presentation.theme.DDanDDanTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

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
fun MyApp(viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val permission = Manifest.permission.ACTIVITY_RECOGNITION

    val permissionGranted = remember { mutableStateOf(checkPermission(context, permission)) }
    val accessToken by viewModel.accessTokenFlow.collectAsState()

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionGranted.value = isGranted
        }

    // 앱 진입 시 권한이 없으면 요청
    LaunchedEffect(Unit) {
        if (!permissionGranted.value) {
            permissionLauncher.launch(permission)
        }
    }

    RenderUI(
        permissionGranted = permissionGranted.value,
        accessToken = accessToken,
        onGoToSettings = { openAppSettings(context) }
    )
}


fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

@Composable
fun RenderUI(
    permissionGranted: Boolean,
    accessToken: String?,
    onGoToSettings: () -> Unit
) {
    when {
        permissionGranted.not() -> {
            PermissionScreen(onGoToSettings)
        }

        permissionGranted && accessToken.isNullOrEmpty() -> {
            NoAccessTokenScreen()
        }

        permissionGranted && !accessToken.isNullOrEmpty() -> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = DDanDDanColorPalette.current.color_background
            ) {
                PassiveDataApp()
            }
        }
    }
}


/** 권한 요청 및 설정 이동 화면 **/
@Composable
fun PermissionScreen(
    onGoToSettings: () -> Unit
) {
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
            Spacer(modifier = Modifier.height(10.dp))

            PermissionChip(onClick = onGoToSettings)
        }
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

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

@Preview(showBackground = true, name = "Permission Screen Preview")
@Composable
fun PermissionScreenPreview() {
    DDanDDanTheme(darkTheme = true) {
        PermissionScreen(
            onGoToSettings = { /* for Preview */ }
        )
    }
}
