package com.example.dicodingevent.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevent.R
import com.example.dicodingevent.databinding.FragmentSettingsBinding
import com.example.dicodingevent.settings.SettingsPreferences
import com.example.dicodingevent.settings.SettingsViewModel
import com.example.dicodingevent.settings.ViewModelFactory
import com.example.dicodingevent.settings.dataStore

class SettingsFragment : Fragment() {
    private lateinit var binding:FragmentSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingsPreferences.getInstance(activity?.application!!.dataStore)

        val switchViewModel = ViewModelProvider(this, ViewModelFactory(pref))
            .get(SettingsViewModel::class.java)

        // get data settings switcher
        switchViewModel.getThemeSettings().observe(this){
                isActive:Boolean ->
            if (isActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switcher.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switcher.isChecked = false
            }
        }

        binding.switcher.setOnCheckedChangeListener { _, isChecked ->
            switchViewModel.saveThemeSettings(isChecked)
        }
    }
}