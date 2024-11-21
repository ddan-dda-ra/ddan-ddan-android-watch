package com.ddanddan.watch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ddanddan.watch.util.PreferencesKeys
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

//class WatchTokenAuthenticator @Inject constructor(
//    private val context: Context,
//    private val dataStore: DataStore<Preferences>
//) : Authenticator {
//
//    //401 시 호출
//    override fun authenticate(route: Route?, response: Response): Request? {
//        val newToken = runBlocking {
//            requestTokenRefreshFromPhone()?.also { token ->
//                // DataStore에 새 토큰 저장
//                dataStore.edit { preferences ->
//                    preferences[PreferencesKeys.ACCESS_TOKEN_KEY] = token
//                }
//            }
//        } ?: return null // 새 토큰이 없으면 인증 실패로 처리
//
//        // 요청에 새 토큰으로 Authorization 헤더 추가
//        return response.request.newBuilder()
//            .header("Authorization", "Bearer $newToken")
//            .build()
//    }
//
//    private suspend fun requestTokenRefreshFromPhone(): String? {
//        val messageClient: MessageClient = Wearable.getMessageClient(context)
//        val node = Wearable.getNodeClient(context).connectedNodes.await().firstOrNull()
//
//        node?.let {
//            val response = messageClient.sendMessage(it.id, "/request_token_refresh", null).await()
//            return String(response) // 새 토큰 반환
//        }
//
//        return null
//    }
//}
