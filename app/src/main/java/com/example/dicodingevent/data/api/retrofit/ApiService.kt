package com.example.dicodingevent.data.api.retrofit

import com.example.dicodingevent.data.api.response.DetailEventResponse
import com.example.dicodingevent.data.api.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/events")
    fun getEvent(@Query("active") active:Int):Call<EventResponse>

    @GET("/events/{id}")
    fun getDetailEvent(@Path("id") id:String):Call<DetailEventResponse>

}