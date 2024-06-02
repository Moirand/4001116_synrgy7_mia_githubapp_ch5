package com.example.data.datasource.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.domain.datastore.SettingsPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

class SettingsPreferencesImpl(private val datastore: DataStore<Preferences>) : SettingsPreferences {
    companion object {
        private val MODE_KEY = booleanPreferencesKey("mode")
    }

    override suspend fun loadMode(): Boolean? {
        return datastore.data.map { preferences ->
            preferences[MODE_KEY]
        }.firstOrNull()
    }

    override suspend fun saveMode(isDarkModeActive: Boolean) {
        datastore.edit { preferences ->
            preferences[MODE_KEY] = isDarkModeActive
        }
    }
}