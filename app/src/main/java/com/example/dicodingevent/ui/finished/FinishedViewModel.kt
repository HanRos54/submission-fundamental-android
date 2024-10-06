package com.example.dicodingevent.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.api.response.Event
import com.example.dicodingevent.data.api.response.EventResponse
import com.example.dicodingevent.data.api.response.ListEventsItem
import com.example.dicodingevent.data.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel: ViewModel() {

    companion object{
        const val TAG = "FinishedViewModel"
    }

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> = _events

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> = _loading

    fun fetchEvents() {
        _loading.value = true
        val client = ApiConfig.getApiService().getEvent(0)

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _events.value = response.body()?.listEvents
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _loading.value = false
                Log.i(TAG, "Error in : ${t.printStackTrace()}")
            }
        })
    }
}