package com.ddanddan.watch.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TokenExpiredReceiver(private val onTokenExpired: () -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.ddanddan.ddanddan.TOKEN_EXPIRED") {
            onTokenExpired()
        }
    }
}
