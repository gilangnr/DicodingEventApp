package com.example.dicodingeventapp.data.remote.retrofit

import com.example.dicodingeventapp.data.remote.response.ResponseDetail
import com.example.dicodingeventapp.data.remote.response.ResponseListEvent
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("/events")
    suspend fun getFinished(@Query("active") active: Int = 0): ResponseListEvent

    @GET("/events")
    suspend fun getUpcoming(@Query("active") active: Int = 1): ResponseListEvent

    @GET("/events")
    suspend fun getUpcoming5(@Query("active") active: Int = 1,
                     @Query("limit") limit: Int = 5
    ): ResponseListEvent

    @GET("/events")
    suspend fun getFinished5(@Query("active") active: Int = 0,
                     @Query("limit") limit: Int = 5
    ): ResponseListEvent

    @GET("/events/{id}")
    fun getEvent(@Path("id") id: String): Call<ResponseDetail>

    @GET("/events")
    fun searchEvents(@Query("q") query: String): Call<ResponseListEvent>
}