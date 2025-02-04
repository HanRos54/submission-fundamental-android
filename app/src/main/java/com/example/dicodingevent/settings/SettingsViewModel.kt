package com.example.dicodingevent.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref:SettingsPreferences): ViewModel()
{
    fun getThemeSettings():LiveData<Boolean>
    {
        return  pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSettings(isDarkModeActive: Boolean)
    {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getLoveSettings():LiveData<Boolean>
    {
        return  pref.getLoveSettings().asLiveData()
    }

    fun saveLoveSettings(fav_love:Boolean)
    {
        viewModelScope.launch {
            pref.saveLoveSettings(fav_love)
        }
    }
}