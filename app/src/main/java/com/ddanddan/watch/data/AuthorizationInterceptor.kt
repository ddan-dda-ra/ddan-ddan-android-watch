package com.ddanddan.watch.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ddanddan.watch.util.PreferencesKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
//    private val dataStore: DataStore<Preferences>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
//        val accessToken = runBlocking {
//            dataStore.data
//                .map { preferences -> preferences[PreferencesKeys.ACCESS_TOKEN_KEY] ?: TEST_TOKEN }
//                .first()
//        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $TEST_TOKEN")
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val TEST_TOKEN = "eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiYWxnIjoiZGlyIiwiZW5jIjoiQTEyOENCQy1IUzI1NiJ9..oTa1EttP0vdSsy_qlKSPOw.FRN058hnNWtPn_ePGZB9jRwBgAAt9i7fKRxGE7jxHioGgsZrmX5KntpITpflmied.G5xaLJxm1Su_3ZRK5h_Hsg"
    }
}
