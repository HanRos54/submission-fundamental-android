package com.example.dicodingevent.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingsPreferences): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java))
        {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Uknown viewModel class: " + modelClass.name)
    }
}