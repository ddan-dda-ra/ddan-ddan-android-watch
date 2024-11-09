package com.ddanddan.watch.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ddanddan.watch.domain.repository.PassiveDataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PassiveDataRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PassiveDataRepository {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "passive_data")

    override val passiveDataEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[DataStoreKeys.PASSIVE_DATA_ENABLED] ?: false
    }

    override suspend fun setPassiveDataEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DataStoreKeys.PASSIVE_DATA_ENABLED] = enabled
        }
    }

    override val latestCalories: Flow<Double> = context.dataStore.data.map { prefs ->
        prefs[DataStoreKeys.LATEST_CALORIES] ?: 0.0
    }

    override suspend fun storeLatestCalories(calories: Double) {
        context.dataStore.edit { prefs ->
            prefs[DataStoreKeys.LATEST_CALORIES] = calories
        }
    }

    companion object {
        object DataStoreKeys {
            val PASSIVE_DATA_ENABLED = booleanPreferencesKey("passive_data_enabled")
            val LATEST_CALORIES = doublePreferencesKey("latest_calories")
        }
    }
}
