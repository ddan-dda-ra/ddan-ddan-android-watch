package com.ddanddan.watch.presentation.service

import android.util.Log
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import timber.log.Timber


class DataLayerListenerService : WearableListenerService() {
    /**
     * - path와 key 값은 송신하는 쪽과 일치해야 함
     * - data가 변경되었을 경우에만 동작함
     */
    override fun onDataChanged(dataEvents: DataEventBuffer) {
        for (event in dataEvents) {
            if (event.type == DataEvent.TYPE_CHANGED && event.dataItem.uri.path == "/access_token") {
                val dataMap = DataMapItem.fromDataItem(event.dataItem).dataMap
                val token = dataMap.getString("accessToken")
                Timber.tag("DataLayerListenerService").d("Received access token: %s", token)
            }
        }
    }
}
