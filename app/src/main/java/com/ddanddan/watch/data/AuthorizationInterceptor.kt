package com.ddanddan.watch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ddanddan.watch.util.PreferencesKeys
import com.ddanddan.watch.util.WatchToPhoneUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            dataStore.data
                .map { preferences -> preferences[PreferencesKeys.ACCESS_TOKEN_KEY] }
                .firstOrNull()
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", accessToken ?: "")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            WatchToPhoneUtils.checkAndSendTokenRefresh(context)
        }

        return response
    }
}
