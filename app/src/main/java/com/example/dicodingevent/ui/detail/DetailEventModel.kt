package com.example.dicodingevent.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.api.response.DetailEventResponse
import com.example.dicodingevent.data.api.response.Event
import com.example.dicodingevent.data.api.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventModel: ViewModel() {

    companion object{
        const val TAG = "DetailEventModel"
    }

    private val _detailEvent = MutableLiveData<Event>()
    val detailEvent:LiveData<Event> = _detailEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    fun fetchDetailEvent(id:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful)
                {
                    _detailEvent.value = response.body()?.event
                    Log.i(TAG, response.body()?.event.toString())
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                Log.i(TAG, t.message.toString())
            }
        })
    }






}