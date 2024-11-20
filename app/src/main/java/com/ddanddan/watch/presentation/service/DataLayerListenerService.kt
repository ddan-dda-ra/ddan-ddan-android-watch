package com.ddanddan.watch.presentation.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ddanddan.watch.util.PreferencesKeys
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * - path와 key 값은 송신하는 쪽과 일치해야 함
 * - data가 변경되었을 경우에만 동작함
 */

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

//    @Inject
//    lateinit var dataStore: DataStore<Preferences>


    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/access_token") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val token = dataMap.getString("accessToken")
                Timber.tag("DataLayerListenerService").d("Received access token: %s", token)

                saveAccessToken(token)
            }
        }
    }

    private fun saveAccessToken(token: String?) {
        token?.let {
//            CoroutineScope(Dispatchers.IO).launch {
//                dataStore.edit { preferences ->
//                    preferences[PreferencesKeys.ACCESS_TOKEN_KEY] = it
//                }
//                Timber.tag("DataLayerListenerService").d("Access token saved to DataStore")
//            }
        }
    }
}
