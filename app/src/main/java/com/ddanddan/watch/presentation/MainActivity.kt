package com.ddanddan.watch.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ddanddan.watch.presentation.service.PassiveDataService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceIntent = Intent(this, PassiveDataService::class.java)
        this.startService(serviceIntent)

        setContent {
            PassiveDataApp()
        }
    }
}
