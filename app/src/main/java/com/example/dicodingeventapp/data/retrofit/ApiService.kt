package com.example.dicodingeventapp.data.retrofit

import com.example.dicodingeventapp.data.response.ResponseDetail
import com.example.dicodingeventapp.data.response.ResponseListEvent
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("/events")
    fun getFinished(@Query("active") active: Int = 0): Call<ResponseListEvent>

    @GET("/events")
    fun getUpcoming(@Query("active") active: Int = 1): Call<ResponseListEvent>

    @GET("/events")
    fun getUpcoming5(@Query("active") active: Int = 1,
                     @Query("limit") limit: Int = 5
    ): Call<ResponseListEvent>

    @GET("/events/{id}")
    fun getEvent(@Path("id") id: String): Call<ResponseDetail>

    @GET("/events")
    fun searchEvents(@Query("q") query: String): Call<ResponseListEvent>
}