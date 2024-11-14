package com.ddanddan.watch.util

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val ACCESS_TOKEN_KEY: Preferences.Key<String> = stringPreferencesKey("access_token")
}
