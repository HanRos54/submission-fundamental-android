package com.example.dicodingevent.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>)
{
    private val THEME_KEY = booleanPreferencesKey("theme_settings")
    private val LOVE = booleanPreferencesKey("fav_lov_settings")

    fun getThemeSetting(): Flow<Boolean>
    {
        return dataStore.data.map { preferences->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive:Boolean)
    {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    fun getLoveSettings(): Flow<Boolean>
    {
        return dataStore.data.map { preferences ->
            preferences[LOVE] ?: false
        }
    }

    suspend fun saveLoveSettings(fav_love:Boolean)
    {
        dataStore.edit {preferences ->
            preferences[LOVE] = fav_love
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreferences
        {
            return INSTANCE ?: synchronized(this){
                val instance = SettingsPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}