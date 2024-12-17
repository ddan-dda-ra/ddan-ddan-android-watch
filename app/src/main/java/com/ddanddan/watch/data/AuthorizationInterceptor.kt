package com.ddanddan.watch.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ddanddan.watch.util.PreferencesKeys
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            dataStore.data
                .map { preferences -> preferences[PreferencesKeys.ACCESS_TOKEN_KEY] }
                .firstOrNull()
        }

        if (accessToken.isNullOrEmpty()) {
            Timber.tag("AuthorizationInterceptor").e("Access token is null. Cancelling request.")
            throw IOException("No access token available, request canceled.")
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", accessToken)
            .build()

        return chain.proceed(request)
    }
}
