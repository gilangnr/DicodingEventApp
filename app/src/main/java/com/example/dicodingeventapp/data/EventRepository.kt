package com.example.dicodingeventapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.dicodingeventapp.data.local.room.EventDao
import com.example.dicodingeventapp.data.remote.response.ListEventsItem
import com.example.dicodingeventapp.data.remote.response.ResponseListEvent
import com.example.dicodingeventapp.data.remote.retrofit.ApiService
import com.example.dicodingeventapp.utils.AppExecutors

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
){

    fun loadFinishedEvents(): LiveData<Result<List<ListEventsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFinished()
            val events = response.listEvents
            val eventList = events.map { events ->
                ListEventsItem(
                    events.summary,
                    events.mediaCover,
                    events.registrants,
                    events.imageLogo,
                    events.link,
                    events.description,
                    events.ownerName,
                    events.cityName,
                    events.quota,
                    events.name,
                    events.id,
                    events.beginTime,
                    events.endTime,
                    events.category
                )
            }
            emit(Result.Success(eventList))
        } catch (e: Exception) {
            Log.d("EventRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }

    }


    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }
}