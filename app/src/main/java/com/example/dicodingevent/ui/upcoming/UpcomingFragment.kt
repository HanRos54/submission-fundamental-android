package com.example.dicodingevent.ui.upcoming

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.R
import com.example.dicodingevent.adapter.EventAdapter
import com.example.dicodingevent.data.api.response.EventResponse
import com.example.dicodingevent.data.api.retrofit.ApiConfig
import com.example.dicodingevent.databinding.FragmentUpcomingBinding
import com.example.dicodingevent.settings.SettingsPreferences
import com.example.dicodingevent.settings.SettingsViewModel
import com.example.dicodingevent.settings.ViewModelFactory
import com.example.dicodingevent.settings.dataStore
import com.example.dicodingevent.ui.finished.FinishedFragment.Companion.TAG
import com.google.android.material.switchmaterial.SwitchMaterial
import retrofit2.Call
import retrofit2.Response

class UpcomingFragment : Fragment() {
    private lateinit var upcomingFragmentBinding:FragmentUpcomingBinding;
    private lateinit var eventAdapter: EventAdapter;

    companion object{
        const val TAG = "UpcomingFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        upcomingFragmentBinding = FragmentUpcomingBinding.inflate(inflater, container, false)


        return upcomingFragmentBinding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = activity?.findViewById<SwitchMaterial>(R.id.switcher)

        val layoutManager = LinearLayoutManager(context)
        upcomingFragmentBinding.upcomingRecycle.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        upcomingFragmentBinding.upcomingRecycle.addItemDecoration(itemDecoration)

        val upcomingModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(UpcomingViewModel::class.java)

        // theme settings vieModel
        val pref = SettingsPreferences.getInstance(activity?.application!!.dataStore)
        val switchViewModel = ViewModelProvider(this, ViewModelFactory(pref))
            .get(SettingsViewModel::class.java)

        // ambil live data event
        upcomingModel.event.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                upcomingFragmentBinding.progressBar.visibility = View.GONE
                eventAdapter = EventAdapter(events)
                upcomingFragmentBinding.upcomingRecycle.adapter = eventAdapter
                Log.i(TAG, "Data: $events")
            }
        }

        // ambil live data loading
        upcomingModel.loading.observe(this, Observer {
            upcomingFragmentBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        // ambil live data fetch retrofit
        upcomingModel.fetchEvents()

        switchViewModel.getThemeSettings().observe(this){
            isActive:Boolean ->
            if (isActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchTheme?.isChecked = false
            }
        }
    }
}